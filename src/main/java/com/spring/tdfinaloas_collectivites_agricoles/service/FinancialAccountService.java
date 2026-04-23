package com.spring.tdfinaloas_collectivites_agricoles.service;

import com.spring.tdfinaloas_collectivites_agricoles.dao.CollectivityDao;
import com.spring.tdfinaloas_collectivites_agricoles.dao.FinancialAccountDao;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Service
public class FinancialAccountService {
    private final FinancialAccountDao financialAccountDao;
    private final CollectivityDao collectivityDao;

    public FinancialAccountService(FinancialAccountDao financialAccountDao, CollectivityDao collectivityDao) {
        this.financialAccountDao = financialAccountDao;
        this.collectivityDao = collectivityDao;
    }

    public Map<String, Object> findByCollectivityIdWithBalanceAtDate(String collectivityId, LocalDate at) throws SQLException {
        if (collectivityDao.findById(collectivityId).isEmpty()) {
            throw new IllegalArgumentException("Collectivity not found: " + collectivityId);
        }

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> accounts = new ArrayList<>();

        List<CashAccount> cashAccounts = financialAccountDao.findCashAccountsByCollectivityId(collectivityId);
        for (CashAccount account : cashAccounts) {
            Map<String, Object> accountInfo = new HashMap<>();
            accountInfo.put("id", account.getId());
            accountInfo.put("type", "CASH");
            accountInfo.put("holderName", account.getHolderName());
            accountInfo.put("balance", getBalanceAtDate(account.getId(), "CASH", at));
            accountInfo.put("createdAt", account.getCreatedAt());
            accounts.add(accountInfo);
        }

        List<BankAccount> bankAccounts = financialAccountDao.findBankAccountsByCollectivityId(collectivityId);
        for (BankAccount account : bankAccounts) {
            Map<String, Object> accountInfo = new HashMap<>();
            accountInfo.put("id", account.getId());
            accountInfo.put("type", "BANK");
            accountInfo.put("holderName", account.getHolderName());
            accountInfo.put("bankName", account.getBankName());
            accountInfo.put("fullAccountNumber", account.getFullAccountNumber());
            accountInfo.put("balance", getBalanceAtDate(account.getId(), "BANK", at));
            accountInfo.put("createdAt", account.getCreatedAt());
            accounts.add(accountInfo);
        }

        List<MobileMoneyAccount> mobileAccounts = financialAccountDao.findMobileAccountsByCollectivityId(collectivityId);
        for (MobileMoneyAccount account : mobileAccounts) {
            Map<String, Object> accountInfo = new HashMap<>();
            accountInfo.put("id", account.getId());
            accountInfo.put("type", "MOBILE");
            accountInfo.put("holderName", account.getHolderName());
            accountInfo.put("mobileService", account.getMobileService());
            accountInfo.put("phoneNumber", account.getPhoneNumber());
            accountInfo.put("balance", getBalanceAtDate(account.getId(), "MOBILE", at));
            accountInfo.put("createdAt", account.getCreatedAt());
            accounts.add(accountInfo);
        }

        result.put("collectivityId", collectivityId);
        result.put("date", at);
        result.put("accounts", accounts);
        result.put("totalBalance", calculateTotalBalance(accounts));

        return result;
    }

    private BigDecimal getBalanceAtDate(String accountId, String accountType, LocalDate at) throws SQLException {
        return financialAccountDao.findBalanceAtDate(accountId, accountType, at);
    }

    private BigDecimal calculateTotalBalance(List<Map<String, Object>> accounts) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map<String, Object> account : accounts) {
            BigDecimal balance = (BigDecimal) account.get("balance");
            if (balance != null) {
                total = total.add(balance);
            }
        }
        return total;
    }
}