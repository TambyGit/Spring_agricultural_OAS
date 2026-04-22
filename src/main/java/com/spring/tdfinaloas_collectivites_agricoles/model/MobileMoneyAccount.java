package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.MobileMoneyService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class MobileMoneyAccount {
    private String id;
    private String holderName;
    private MobileMoneyService mobileService;
    private String phoneNumber;
    private BigDecimal balance;
    private String collectivityId;
    private LocalDateTime createdAt;

    public boolean isValidPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("(032|033|034|038|039)\\d{7}");
    }

    public MobileMoneyAccount() {
    }

    public MobileMoneyAccount(String id, String holderName, MobileMoneyService mobileService, String phoneNumber, BigDecimal balance, String collectivityId, LocalDateTime createdAt) {
        this.id = id;
        this.holderName = holderName;
        this.mobileService = mobileService;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.collectivityId = collectivityId;
        this.createdAt = createdAt;
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

    public MobileMoneyService getMobileService() {
        return mobileService;
    }

    public void setMobileService(MobileMoneyService mobileService) {
        this.mobileService = mobileService;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
        MobileMoneyAccount that = (MobileMoneyAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && mobileService == that.mobileService && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(balance, that.balance) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, mobileService, phoneNumber, balance, collectivityId, createdAt);
    }

    @Override
    public String toString() {
        return "MobileMoneyAccount{" +
                "id='" + id + '\'' +
                ", holderName='" + holderName + '\'' +
                ", mobileService=" + mobileService +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                ", collectivityId='" + collectivityId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
