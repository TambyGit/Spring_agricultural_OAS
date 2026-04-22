package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.CustomDataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.Transaction;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.PaymentMode;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionDao {
    private final CustomDataSource customDataSource;

    public TransactionDao(CustomDataSource customDataSource) {
        this.customDataSource = customDataSource;
    }

    public void save(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (id, collectivity_id, member_id, amount, payment_mode, account_credited_id, transaction_date, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getCollectivityId());
            stmt.setString(3, transaction.getMemberId());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5, transaction.getPaymentMode().name());
            stmt.setString(6, transaction.getAccountCreditedId());
            stmt.setTimestamp(7, Timestamp.valueOf(transaction.getTransactionDate()));
            stmt.setString(8, transaction.getType());
            stmt.executeUpdate();
        }
    }

    public List<Transaction> findByCollectivityIdAndDateRange(String collectivityId, LocalDate from, LocalDate to) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE collectivity_id = ? AND DATE(transaction_date) BETWEEN ? AND ? ORDER BY transaction_date DESC";
        try (Connection conn = (Connection) customDataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            stmt.setDate(2, Date.valueOf(from));
            stmt.setDate(3, Date.valueOf(to));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getString("id"));
                transaction.setCollectivityId(rs.getString("collectivity_id"));
                transaction.setMemberId(rs.getString("member_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
                transaction.setAccountCreditedId(rs.getString("account_credited_id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setType(rs.getString("type"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}