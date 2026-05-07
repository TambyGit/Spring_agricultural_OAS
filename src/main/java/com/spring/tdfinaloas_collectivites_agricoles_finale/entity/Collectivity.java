package com.spring.tdfinaloas_collectivites_agricoles_finale.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Collectivity {
    private String id;
    private String name;
    private Integer number;
    private String location;
    private String specialization;
    private CollectivityStructure collectivityStructure;
    private List<Member> members;
    private Boolean federationApproval;

    public boolean hasEnoughMembers() {
        return members.size() >= 10;
    }

    public List<Member> addMembers(List<Member> newMembers) {
        if(members == null){
            members = new ArrayList<>();
        }
        for (Member member : newMembers) {
            member.getCollectivities().add(this);
        }
        members.addAll(newMembers);

        return members;
    }

    // Montant total encaissé sur une période (toutes les transactions IN des comptes)
    public Double getTotalCollectedBetween(List<FinancialAccount> accounts, LocalDate from, LocalDate to) {
        return accounts.stream()
                .flatMap(account -> account.getTransactions().stream())
                .filter(tx -> !tx.getCreationDate().isBefore(from) && !tx.getCreationDate().isAfter(to))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // Montant impayé potentiel par membre = somme des cotisations ACTIVE - ce qu'il a payé
    public Double getPotentialUnpaidPerMember(List<MembershipFee> fees, List<MemberPayment> memberPayments) {
        double totalActiveFees = fees.stream()
                .filter(fee -> ActivityStatus.ACTIVE.equals(fee.getStatus()))
                .mapToDouble(MembershipFee::getAmount)
                .sum();

        double totalPaid = memberPayments.stream()
                .mapToDouble(MemberPayment::getAmount)
                .sum();

        return Math.max(0.0, totalActiveFees - totalPaid);
    }
}
