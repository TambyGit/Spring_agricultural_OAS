package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private Integer id;
    private String memberId;
    private String collectivityId;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
    private Double amount;
    private LocalDateTime paymentDate;

    public Payment() {
    }
    public Payment(Integer id, String memberId, String collectivityId, Boolean registrationFeePaid, Boolean membershipDuesPaid, Double amount, LocalDateTime paymentDate) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.registrationFeePaid = registrationFeePaid;
        this.membershipDuesPaid = membershipDuesPaid;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public Boolean getRegistrationFeePaid() {
        return registrationFeePaid;
    }

    public void setRegistrationFeePaid(Boolean registrationFeePaid) {
        this.registrationFeePaid = registrationFeePaid;
    }

    public Boolean getMembershipDuesPaid() {
        return membershipDuesPaid;
    }

    public void setMembershipDuesPaid(Boolean membershipDuesPaid) {
        this.membershipDuesPaid = membershipDuesPaid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && Objects.equals(memberId, payment.memberId) && Objects.equals(collectivityId, payment.collectivityId) && Objects.equals(registrationFeePaid, payment.registrationFeePaid) && Objects.equals(membershipDuesPaid, payment.membershipDuesPaid) && Objects.equals(amount, payment.amount) && Objects.equals(paymentDate, payment.paymentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, collectivityId, registrationFeePaid, membershipDuesPaid, amount, paymentDate);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", collectivityId='" + collectivityId + '\'' +
                ", registrationFeePaid=" + registrationFeePaid +
                ", membershipDuesPaid=" + membershipDuesPaid +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
