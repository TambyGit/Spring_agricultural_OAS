package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.CustomDataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.BankName;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.MobileMoneyService;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class FinancialAccountDao {
    private final CustomDataSource customDataSource;

    public FinancialAccountDao(CustomDataSource customDataSource) {
        this.customDataSource= customDataSource;
    }

    public void saveCashAccount(CashAccount account) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM cash_accounts WHERE collectivity_id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, account.getCollectivityId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("A cash account already exists for this collectivity");
            }
        }
        String sql = "INSERT INTO cash_accounts (id, holder_name, balance, collectivity_id, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.executeUpdate();
        }
    }

    public void saveBankAccount(BankAccount account) throws SQLException {
        if (!account.isValidRIB()) {
            throw new SQLException("Invalid RIB format");
        }
        String sql = "INSERT INTO bank_accounts (id, holder_name, balance, collectivity_id, created_at, bank_name, bank_code, branch_code, account_number, rib_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setString(6, account.getBankName().name());
            stmt.setString(7, account.getBankCode());
            stmt.setString(8, account.getBranchCode());
            stmt.setString(9, account.getAccountNumber());
            stmt.setString(10, account.getRibKey());
            stmt.executeUpdate();
        }
    }

    public void saveMobileMoneyAccount(MobileMoneyAccount account) throws SQLException {
        if (!account.isValidPhoneNumber()) {
            throw new SQLException("Invalid phone number format");
        }
        String sql = "INSERT INTO mobile_money_accounts (id, holder_name, balance, collectivity_id, created_at, mobile_service, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setString(6, account.getMobileService().name());
            stmt.setString(7, account.getPhoneNumber());
            stmt.executeUpdate();
        }
    }

    public List<CashAccount> findCashAccountsByCollectivityId(String collectivityId) throws SQLException {
        List<CashAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM cash_accounts WHERE collectivity_id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CashAccount account = new CashAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                accounts.add(account);
            }
        }
        return accounts;
    }

    public List<BankAccount> findBankAccountsByCollectivityId(String collectivityId) throws SQLException {
        List<BankAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM bank_accounts WHERE collectivity_id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BankAccount account = new BankAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setBankName(BankName.valueOf(rs.getString("bank_name")));
                account.setBankCode(rs.getString("bank_code"));
                account.setBranchCode(rs.getString("branch_code"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setRibKey(rs.getString("rib_key"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    public List<MobileMoneyAccount> findMobileAccountsByCollectivityId(String collectivityId) throws SQLException {
        List<MobileMoneyAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM mobile_money_accounts WHERE collectivity_id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MobileMoneyAccount account = new MobileMoneyAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setMobileService(MobileMoneyService.valueOf(rs.getString("mobile_service")));
                account.setPhoneNumber(rs.getString("phone_number"));
                accounts.add(account);
            }
        }
        return accounts;
    }

    public BigDecimal findBalanceAtDate(String accountId, String accountType, LocalDate at) throws SQLException {
        String tableName = getTableName(accountType);
        String sql = "SELECT balance FROM " + tableName + " WHERE id = ? AND DATE(created_at) <= ? ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountId);
            stmt.setDate(2, Date.valueOf(at));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("balance");
            }
            return BigDecimal.ZERO;
        }
    }

    private Optional<CashAccount> findCashAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM cash_accounts WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CashAccount account = new CashAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    private Optional<FinancialAccount> findBankAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM bank_accounts WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BankAccount account = new BankAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setBankName(BankName.valueOf(rs.getString("bank_name")));
                account.setBankCode(rs.getString("bank_code"));
                account.setBranchCode(rs.getString("branch_code"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setRibKey(rs.getString("rib_key"));
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    private Optional<MobileMoneyAccount> findMobileAccountById(String id) throws SQLException {
        String sql = "SELECT * FROM mobile_money_accounts WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MobileMoneyAccount account = new MobileMoneyAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setMobileService(MobileMoneyService.valueOf(rs.getString("mobile_service")));
                account.setPhoneNumber(rs.getString("phone_number"));
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    public void updateBalance(String accountId, String accountType, BigDecimal newBalance) throws SQLException {
        String tableName = getTableName(accountType);
        String sql = "UPDATE " + tableName + " SET balance = ? WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection();PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newBalance);
            stmt.setString(2, accountId);
            stmt.executeUpdate();
        }
    }

    private String getTableName(String accountType) {
        switch (accountType) {
            case "CASH": return "cash_accounts";
            case "BANK": return "bank_accounts";
            case "MOBILE": return "mobile_money_accounts";
            default: throw new IllegalArgumentException("Unknown account type");
        }
    }
}