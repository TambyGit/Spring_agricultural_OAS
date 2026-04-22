package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MemberPayment {
    private String id;
    private String memberId;
    private String membershipFeeId;
    private Double amount;
    private PaymentMode paymentMode;
    private String accountCreditedId;
    private LocalDateTime paymentDate;
    private Double federationShare;

    public MemberPayment() {
    }

    public MemberPayment(String id, String memberId, String membershipFeeId, Double amount, PaymentMode paymentMode, String accountCreditedId, LocalDateTime paymentDate, Double federationShare) {
        this.id = id;
        this.memberId = memberId;
        this.membershipFeeId = membershipFeeId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCreditedId = accountCreditedId;
        this.paymentDate = paymentDate;
        this.federationShare = federationShare;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMembershipFeeId() {
        return membershipFeeId;
    }

    public void setMembershipFeeId(String membershipFeeId) {
        this.membershipFeeId = membershipFeeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getAccountCreditedId() {
        return accountCreditedId;
    }

    public void setAccountCreditedId(String accountCreditedId) {
        this.accountCreditedId = accountCreditedId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getFederationShare() {
        return federationShare;
    }

    public void setFederationShare(Double federationShare) {
        this.federationShare = federationShare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberPayment that = (MemberPayment) o;
        return Objects.equals(id, that.id) && Objects.equals(memberId, that.memberId) && Objects.equals(membershipFeeId, that.membershipFeeId) && Objects.equals(amount, that.amount) && Objects.equals(paymentMode, that.paymentMode) && Objects.equals(accountCreditedId, that.accountCreditedId) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(federationShare, that.federationShare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, membershipFeeId, amount, paymentMode, accountCreditedId, paymentDate, federationShare);
    }

    @Override
    public String toString() {
        return "MemberPayment{" +
                "id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", membershipFeeId='" + membershipFeeId + '\'' +
                ", amount=" + amount +
                ", paymentMode='" + paymentMode + '\'' +
                ", accountCreditedId='" + accountCreditedId + '\'' +
                ", paymentDate=" + paymentDate +
                ", federationShare=" + federationShare +
                '}';
    }
}