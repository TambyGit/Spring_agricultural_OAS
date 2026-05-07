package com.spring.tdfinaloas_collectivites_agricoles_finale.service;

import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.CollectivityStatistics;
import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.FederationStatistics;
import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.MemberAttendanceRate;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.*;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.BadRequestException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.NotFoundException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static edu.hei.school.agricultural.entity.ActivityStatus.ACTIVE;
import static edu.hei.school.agricultural.entity.PaymentMode.*;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class CollectivityService {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final ActivityRepository activityRepository;

    public List<Collectivity> createCollectivities(List<Collectivity> collectivities) {
        for (Collectivity collectivity : collectivities) {
            if (!collectivity.hasEnoughMembers()) {
                throw new BadRequestException("Collectivity must have at least 10 members, otherwise actual is " + collectivity.getMembers().size());
            }
            collectivity.setId(randomUUID().toString());
        }
        return collectivityRepository.saveAll(collectivities);
    }

    public Collectivity getCollectivityById(String id) {
        return collectivityRepository.findById(id).orElseThrow(() -> new NotFoundException("Collectivity.id= " + id + " not found"));
    }

    public Collectivity updateInformations(String collectivityId, String actualName, Integer actualNumber) {
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));
        if (actualNumber != null && collectivityRepository.isNumberExists(actualNumber)) {
            throw new BadRequestException("Collectivity.number=" + actualNumber + " already exists");
        }
        if (actualName != null && collectivityRepository.isNameExists(actualName)) {
            throw new BadRequestException("Collectivity.name=" + actualName + " already exists");
        }
        collectivity.setName(actualName);
        collectivity.setNumber(actualNumber);
        return collectivityRepository.saveAll(List.of((collectivity))).getFirst();
    }

    public List<MembershipFee> getMembershipFeesByCollectivityIdentifier(String collectivityIdentifier) {
        Collectivity collectivity = collectivityRepository.findById(collectivityIdentifier)
                .orElseThrow(() ->
                        new NotFoundException("Collectivity.id= " + collectivityIdentifier + " not found"));

        return membershipFeeRepository.getMembershipFeesByCollectivityId(collectivity.getId());
    }

    public List<MembershipFee> createMembershipFees(String collectivityIdentifier, List<MembershipFee> membershipFees) {
        Collectivity collectivity = collectivityRepository.findById(collectivityIdentifier)
                .orElseThrow(() ->
                        new NotFoundException("Collectivity.id= " + collectivityIdentifier + " not found"));
        for (MembershipFee membershipFee : membershipFees) {
            membershipFee.setId(randomUUID().toString());
            membershipFee.setStatus(ACTIVE);
            membershipFee.setCollectivityOwner(collectivity);
        }
        return membershipFeeRepository.saveAll(membershipFees);
    }

    public List<FinancialAccount> getFinancialAccounts(String collectivityIdentifier) {
        Collectivity collectivity = collectivityRepository.findById(collectivityIdentifier)
                .orElseThrow(() ->
                        new NotFoundException("Collectivity.id= " + collectivityIdentifier + " not found"));

        CashAccount cashAccount = financialAccountRepository.getCashAccountByCollectivityId(collectivity.getId());
        List<BankAccount> bankAccounts = financialAccountRepository.getBankAccountsByCollectivityId(collectivity.getId());
        List<MobileBankingAccount> mobileBankingAccountsByCollectivityId = financialAccountRepository.getMobileBankingAccountsByCollectivityId(collectivity.getId());

        return Stream.concat(
                Stream.concat(
                        Stream.of(cashAccount),
                        bankAccounts.stream()),
                mobileBankingAccountsByCollectivityId.stream()
        ).toList();
    }

    public List<CollectivityTransaction> getTransactionsByCollectivity(String collectivityIdentifier, LocalDate from, LocalDate to) {
        List<FinancialAccount> financialAccounts = getFinancialAccounts(collectivityIdentifier);

        return financialAccounts.stream()
                .map(financialAccount -> {
                    var transactionList = financialAccount.getTransactions().stream()
                            .filter(transaction -> (transaction.getCreationDate().isAfter(from) || transaction.getCreationDate().equals(from))
                                    && (transaction.getCreationDate().isBefore(to) || transaction.getCreationDate().equals(to)))
                            .toList();
                    var paymentMode = getPaymentMode(financialAccount);
                    return transactionList.stream()
                            .map(transaction -> {
                                CollectivityTransaction collectivityTransaction = CollectivityTransaction.builder()
                                        .id(transaction.getId())
                                        .type(transaction.getType())
                                        .amount(transaction.getAmount())
                                        .creationDate(transaction.getCreationDate())
                                        .accountCredited(financialAccount)
                                        .paymentMode(paymentMode)
                                        .memberDebited(transaction.getMemberDebited())
                                        .build();
                                return collectivityTransaction;
                            })
                            .toList();
                })
                .flatMap(List::stream)
                .toList();
    }

    private PaymentMode getPaymentMode(FinancialAccount financialAccount) {
        PaymentMode paymentMode;
        paymentMode = switch (financialAccount) {
            case BankAccount ignored -> BANK_TRANSFER;
            case MobileBankingAccount ignored -> MOBILE_BANKING;
            case CashAccount ignored -> CASH;
            default ->
                    throw new IllegalArgumentException("Unknown financial account type " + financialAccount.getClass().getTypeName());
        };
        return paymentMode;
    }

    public CollectivityStatistics getStatisticsByCollectivity(String collectivityId, LocalDate from, LocalDate to) {
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity.id= " + collectivityId + " not found"));

        List<FinancialAccount> accounts = getFinancialAccounts(collectivityId);
        Double totalCollected = collectivity.getTotalCollectedBetween(accounts, from, to);

        List<MembershipFee> activeFees = membershipFeeRepository
                .getMembershipFeesByCollectivityId(collectivityId)
                .stream()
                .filter(fee -> ActivityStatus.ACTIVE.equals(fee.getStatus()))
                .toList();

        List<Member> members = memberRepository.findAllByCollectivity(collectivity);

        double totalUnpaid = 0.0;
        for (Member member : members) {
            List<MemberPayment> payments = memberPaymentRepository
                    .findByMemberIdAndPeriod(member.getId(), from, to);
            totalUnpaid += collectivity.getPotentialUnpaidPerMember(activeFees, payments);
        }
        double unpaidPerMember = members.isEmpty() ? 0.0 : totalUnpaid / members.size();

        // ← le calcul d'assiduité est ICI, dans la méthode
        long totalActivities = activityRepository
                .countActivitiesByCollectivityAndPeriod(collectivityId, from, to);

        List<MemberAttendanceRate> memberRates = members.stream().map(member -> {
            long presences = activityRepository
                    .countPresencesByMemberAndPeriod(member.getId(), collectivityId, from, to);
            double rate = totalActivities == 0 ? 0.0 : (presences * 100.0) / totalActivities;
            return MemberAttendanceRate.builder()
                    .memberId(member.getId())
                    .firstName(member.getFirstName())
                    .lastName(member.getLastName())
                    .attendanceRate(rate)
                    .build();
        }).toList();

        return CollectivityStatistics.builder()
                .from(from)
                .to(to)
                .totalCollectedAmount(totalCollected)
                .potentialUnpaidAmountPerMember(unpaidPerMember)
                .memberAttendanceRates(memberRates)  // ← ajouter ce champ
                .build();
    }

    public List<FederationStatistics> getFederationStatistics(LocalDate from, LocalDate to) {
        List<String> collectivityIds = List.of("col-1", "col-2", "col-3");

        return collectivityIds.stream().map(collectivityId -> {
            Collectivity collectivity = collectivityRepository.findById(collectivityId).orElseThrow();
            List<Member> members = memberRepository.findAllByCollectivity(collectivity);
            List<MembershipFee> fees = membershipFeeRepository.getMembershipFeesByCollectivityId(collectivityId);

            long upToDateCount = members.stream().filter(member -> {
                List<MemberPayment> payments = memberPaymentRepository
                        .findByMemberIdAndPeriod(member.getId(), from, to);
                return member.isUpToDateForActiveFees(fees, payments);
            }).count();

            double percentage = members.isEmpty() ? 0.0 : (upToDateCount * 100.0) / members.size();

            List<Member> newMembers = memberRepository
                    .findNewMembersByCollectivityAndPeriod(collectivityId, from, to);

            // ← le calcul d'assiduité global est ICI, dans la méthode
            long totalActivities = activityRepository
                    .countActivitiesByCollectivityAndPeriod(collectivityId, from, to);

            double globalRate = members.isEmpty() ? 0.0 : members.stream().mapToDouble(member -> {
                long presences = activityRepository
                        .countPresencesByMemberAndPeriod(member.getId(), collectivityId, from, to);
                return totalActivities == 0 ? 0.0 : (presences * 100.0) / totalActivities;
            }).average().orElse(0.0);

            return FederationStatistics.builder()
                    .collectivityId(collectivityId)
                    .collectivityName(collectivity.getName())
                    .from(from)
                    .to(to)
                    .upToDateMembersPercentage(percentage)
                    .newMembersCount(newMembers.size())
                    .globalAttendanceRate(globalRate)
                    .build();
        }).toList();
    }


}
