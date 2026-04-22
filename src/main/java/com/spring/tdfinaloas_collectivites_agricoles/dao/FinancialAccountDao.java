package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.DataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class FinancialAccountDao {
    private final DataSource dataSource;

    public FinancialAccountDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void saveCashAccount(CashAccount account) throws SQLException {

        String checkSql = "SELECT COUNT(*) FROM cash_accounts WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, account.getCollectivityId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("A cash account already exists for this collectivity");
            }
        }

        String sql = "INSERT INTO cash_accounts (id, holder_name, balance, collectivity_id, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.executeUpdate();
        }
    }


    public void saveBankAccount(BankAccount account) throws SQLException {
        if (!account.isValidRIB()) {
            throw new SQLException("Invalid RIB format");
        }

        String sql = "INSERT INTO bank_accounts (id, holder_name, balance, collectivity_id, created_at, " +
                "bank_name, bank_code, branch_code, account_number, rib_key) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setString(6, account.getBankName());
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

        String sql = "INSERT INTO mobile_money_accounts (id, holder_name, balance, collectivity_id, created_at, " +
                "mobile_service, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, account.getId());
            stmt.setString(2, account.getHolderName());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getCollectivityId());
            stmt.setTimestamp(5, Timestamp.valueOf(account.getCreatedAt()));
            stmt.setString(6, account.getMobileService());
            stmt.setString(7, account.getPhoneNumber());
            stmt.executeUpdate();
        }
    }


    public List<FinancialAccount> findByCollectivityId(String collectivityId) throws SQLException {
        List<FinancialAccount> accounts = new ArrayList<>();


        String cashSql = "SELECT * FROM cash_accounts WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(cashSql)) {
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


        String bankSql = "SELECT * FROM bank_accounts WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(bankSql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BankAccount account = new BankAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setBankName(rs.getString("bank_name"));
                account.setBankCode(rs.getString("bank_code"));
                account.setBranchCode(rs.getString("branch_code"));
                account.setAccountNumber(rs.getString("account_number"));
                account.setRibKey(rs.getString("rib_key"));
                accounts.add(account);
            }
        }


        String mobileSql = "SELECT * FROM mobile_money_accounts WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(mobileSql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MobileMoneyAccount account = new MobileMoneyAccount();
                account.setId(rs.getString("id"));
                account.setHolderName(rs.getString("holder_name"));
                account.setBalance(rs.getBigDecimal("balance"));
                account.setCollectivityId(rs.getString("collectivity_id"));
                account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                account.setMobileService(rs.getString("mobile_service"));
                account.setPhoneNumber(rs.getString("phone_number"));
                accounts.add(account);
            }
        }

        return accounts;
    }


    public void updateBalance(String accountId, String accountType, BigDecimal newBalance) throws SQLException {
        String tableName = getTableName(accountType);
        String sql = "UPDATE " + tableName + " SET balance = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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