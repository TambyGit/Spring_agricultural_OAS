package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.mapper;

import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.CreateMemberPayment;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.*;
import com.spring.tdfinaloas_collectivites_agricoles_finale.exception.NotFoundException;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.FinancialAccountRepository;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.MemberRepository;
import com.spring.tdfinaloas_collectivites_agricoles_finale.repository.MembershipFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPaymentDtoMaper {
    private final edu.hei.school.agricultural.controller.mapper.FinancialAccountDtoMapper financialAccountDtoMapper;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final FinancialAccountRepository financialAccountRepository;

    public MemberPayment mapToEntity(String memberIdentifier, CreateMemberPayment createMemberPayment) {
        Member member = memberRepository.findById(memberIdentifier).orElseThrow(
                () -> new NotFoundException("Member.id=" + memberIdentifier + " not found")
        );
        MembershipFee membershipFee = membershipFeeRepository.findById(createMemberPayment.getMembershipFeeIdentifier())
                .orElseThrow(
                        () -> new NotFoundException("MembershipFee.id=" + createMemberPayment.getMembershipFeeIdentifier() + " not found")
                );
        FinancialAccount financialAccount = financialAccountRepository.findFinancialAccountById(createMemberPayment.getAccountCreditedIdentifier())
                .orElseThrow(() -> new NotFoundException("FinancialAccount.id=" + createMemberPayment.getAccountCreditedIdentifier() + " not found"));

        return MemberPayment.builder()
                .paymentMode(createMemberPayment.getPaymentMode() == null ? null : PaymentMode.valueOf(createMemberPayment.getPaymentMode().name()))
                .amount(createMemberPayment.getAmount())
                .memberOwner(member)
                .membershipFee(membershipFee)
                .accountCredited(financialAccount)
                .build();
    }

    public MemberPayment mapToDto(MemberPayment memberPayment) {
        return MemberPayment.builder()
                .id(memberPayment.getId())
                .paymentMode(memberPayment.getPaymentMode() == null ? null : PaymentMode.valueOf(memberPayment.getPaymentMode().name()))
                .accountCredited(financialAccountDtoMapper.mapToDto(memberPayment.getAccountCredited()))
                .creationDate(memberPayment.getCreationDate())
                .amount(memberPayment.getAmount())
                .build();
    }
}
