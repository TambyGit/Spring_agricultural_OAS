package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.BankName;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class BankAccount extends FinancialAccount {
    private String id;
    private String holderName;
    private BankName bankName;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String ribKey;
    private BigDecimal balance;
    private String collectivityId;
    private LocalDateTime createdAt;

    public boolean isValidRIB() {
        if (bankCode == null || !bankCode.matches("\\d{5}")) return false;
        if (branchCode == null || !branchCode.matches("\\d{5}")) return false;
        if (accountNumber == null || !accountNumber.matches("\\d{11}")) return false;
        if (ribKey == null || !ribKey.matches("\\d{2}")) return false;
        return true;
    }

    public String getFullAccountNumber() {
        return bankCode + branchCode + accountNumber + ribKey;
    }

    public BankAccount() {
    }

    public BankAccount(String id, String holderName, BankName bankName, String bankCode, String branchCode, String accountNumber, String ribKey, BigDecimal balance, String collectivityId, LocalDateTime createdAt) {
        super();
        this.id = id;
        this.holderName = holderName;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.accountNumber = accountNumber;
        this.ribKey = ribKey;
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

    public BankName getBankName() {
        return bankName;
    }

    public void setBankName(BankName bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRibKey() {
        return ribKey;
    }

    public void setRibKey(String ribKey) {
        this.ribKey = ribKey;
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
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(holderName, that.holderName) && bankName == that.bankName && Objects.equals(bankCode, that.bankCode) && Objects.equals(branchCode, that.branchCode) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(ribKey, that.ribKey) && Objects.equals(balance, that.balance) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, holderName, bankName, bankCode, branchCode, accountNumber, ribKey, balance, collectivityId, createdAt);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id='" + id + '\'' +
                ", holderName='" + holderName + '\'' +
                ", bankName=" + bankName +
                ", bankCode='" + bankCode + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ribKey='" + ribKey + '\'' +
                ", balance=" + balance +
                ", collectivityId='" + collectivityId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
