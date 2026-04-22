package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.BankName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankAccount extends FinancialAccount{
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
}
