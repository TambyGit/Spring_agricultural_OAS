package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class FinancialAccount {
    protected String id;
    protected String holderName;
    protected BigDecimal balance;
    protected String collectivityId;
    protected LocalDateTime createdAt;

    public FinancialAccount(String id, String holderName, BigDecimal balance, String collectivityId, LocalDateTime createdAt) {
        this.id = id;
        this.holderName = holderName;
        this.balance = balance;
        this.collectivityId = collectivityId;
        this.createdAt = createdAt;
    }

    public FinancialAccount() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialAccount that = (FinancialAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && Objects.equals(balance, that.balance) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, balance, collectivityId, createdAt);
    }

    @Override
    public String toString() {
        return "FinancialAccount{" +
                "id='" + id + '\'' +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                ", collectivityId='" + collectivityId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
