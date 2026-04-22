package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.CustomDataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.MemberPayment;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.PaymentMode;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberPaymentDao {
    private final CustomDataSource customDataSource;

    public MemberPaymentDao(CustomDataSource customDataSource) {
        this.customDataSource = customDataSource;
    }

    public void save(MemberPayment payment) throws SQLException {
        String sql = "INSERT INTO member_payments (id, member_id, membership_fee_id, amount, payment_mode, account_credited_id, payment_date, federation_share) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getId());
            stmt.setString(2, payment.getMemberId());
            stmt.setString(3, payment.getMembershipFeeId());
            stmt.setDouble(4, payment.getAmount());
            stmt.setString(5, payment.getPaymentMode().name());
            stmt.setString(6, payment.getAccountCreditedId());
            stmt.setTimestamp(7, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setDouble(8, payment.getFederationShare());
            stmt.executeUpdate();
        }
    }

    public List<MemberPayment> findByMemberId(String memberId) throws SQLException {
        List<MemberPayment> payments = new ArrayList<>();
        String sql = "SELECT * FROM member_payments WHERE member_id = ? ORDER BY payment_date DESC";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MemberPayment payment = new MemberPayment();
                payment.setId(rs.getString("id"));
                payment.setMemberId(rs.getString("member_id"));
                payment.setMembershipFeeId(rs.getString("membership_fee_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
                payment.setAccountCreditedId(rs.getString("account_credited_id"));
                payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                payment.setFederationShare(rs.getDouble("federation_share"));
                payments.add(payment);
            }
        }
        return payments;
    }

    public Optional<MemberPayment> findById(String id) throws SQLException {
        String sql = "SELECT * FROM member_payments WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MemberPayment payment = new MemberPayment();
                payment.setId(rs.getString("id"));
                payment.setMemberId(rs.getString("member_id"));
                payment.setMembershipFeeId(rs.getString("membership_fee_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
                payment.setAccountCreditedId(rs.getString("account_credited_id"));
                payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                payment.setFederationShare(rs.getDouble("federation_share"));
                return Optional.of(payment);
            }
        }
        return Optional.empty();
    }
}