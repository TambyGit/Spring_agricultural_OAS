package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.PaymentMode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private String id;
    private String collectivityId;
    private String memberId;
    private Double amount;
    private PaymentMode paymentMode;
    private String accountCreditedId;
    private LocalDateTime transactionDate;
    private String type;

    public Transaction() {
    }

    public Transaction(String id, String collectivityId, String memberId, Double amount, PaymentMode paymentMode, String accountCreditedId, LocalDateTime transactionDate, String type) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.memberId = memberId;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.accountCreditedId = accountCreditedId;
        this.transactionDate = transactionDate;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(memberId, that.memberId) && Objects.equals(amount, that.amount) && Objects.equals(paymentMode, that.paymentMode) && Objects.equals(accountCreditedId, that.accountCreditedId) && Objects.equals(transactionDate, that.transactionDate) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, collectivityId, memberId, amount, paymentMode, accountCreditedId, transactionDate, type);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", collectivityId='" + collectivityId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", amount=" + amount +
                ", paymentMode='" + paymentMode + '\'' +
                ", accountCreditedId='" + accountCreditedId + '\'' +
                ", transactionDate=" + transactionDate +
                ", type='" + type + '\'' +
                '}';
    }
}
