package com.spring.tdfinaloas_collectivites_agricoles.service;

import com.spring.tdfinaloas_collectivites_agricoles.dao.*;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {

    private final MemberDao memberDao;
    private final MembershipFeeDao membershipFeeDao;
    private final FinancialAccountDao financialAccountDao;
    private final TransactionDao transactionDao;
    private final CollectivityDao collectivityDao;

    public PaymentService(MemberDao memberDao, MembershipFeeDao membershipFeeDao,
                          FinancialAccountDao financialAccountDao, TransactionDao transactionDao,
                          CollectivityDao collectivityDao) {
        this.memberDao = memberDao;
        this.membershipFeeDao = membershipFeeDao;
        this.financialAccountDao = financialAccountDao;
        this.transactionDao = transactionDao;
        this.collectivityDao = collectivityDao;
    }

    @Transactional
    public List<MemberPayment> createPayments(String memberId, List<MemberPayment> paymentRequests) throws SQLException {
        List<MemberPayment> createdPayments = new ArrayList<>();


        Optional<Member> memberOpt = memberDao.findById(memberId);
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("Member not found: " + memberId);
        }

        Member member = memberOpt.get();
        String collectivityId = member.getId();


        Optional<Collectivity> collectivityOpt = collectivityDao.findById(collectivityId);
        if (collectivityOpt.isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found for this member");
        }

        for (MemberPayment request : paymentRequests) {

            Optional<MembershipFee> feeOpt = membershipFeeDao.findById(request.getMembershipFeeId());
            if (feeOpt.isEmpty()) {
                throw new IllegalArgumentException("Membership fee not found: " + request.getMembershipFeeId());
            }

            MembershipFee fee = feeOpt.get();


            if (request.getAmount() <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }


            MemberPayment payment = new MemberPayment();
            payment.setId(UUID.randomUUID().toString());
            payment.setMemberId(memberId);
            payment.setMembershipFeeId(request.getMembershipFeeId());
            payment.setAmount(request.getAmount());
            payment.setPaymentMode(request.getPaymentMode());
            payment.setAccountCreditedId(request.getMembershipFeeId());
            payment.setPaymentDate(LocalDateTime.now());


            Double federationShare = payment.getAmount() * (0.01);
            payment.setFederationShare(federationShare);


            Transaction transaction = new Transaction();
            transaction.setId(UUID.randomUUID().toString());
            transaction.setCollectivityId(collectivityId);
            transaction.setMemberId(memberId);
            transaction.setAmount(payment.getAmount());
            transaction.setPaymentMode(request.getPaymentMode());
            transaction.setAccountCreditedId(request.getAccountCreditedId());
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setType("MEMBERSHIP_FEE");


            updateAccountBalance(request.getAccountCreditedId(), payment.getAmount());


            savePayment(payment);
            transactionDao.save(transaction);

            createdPayments.add(payment);
        }

        return createdPayments;
    }

    private void updateAccountBalance(String accountId, Double amount) throws SQLException {

    }

    private void savePayment(MemberPayment payment) throws SQLException {
        String sql = "INSERT INTO member_payments (id, member_id, membership_fee_id, amount, payment_mode, " +
                "account_credited_id, payment_date, federation_share) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    }
}