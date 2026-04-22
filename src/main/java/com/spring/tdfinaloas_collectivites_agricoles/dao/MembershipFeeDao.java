package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.DataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.MembershipFee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class MembershipFeeDao {
    private final DataSource dataSource;

    public MembershipFeeDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(MembershipFee fee) throws SQLException {
        String sql = "INSERT INTO membership_fees (id, collectivity_id, eligible_from, frequency, amount, label, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fee.getId());
            stmt.setString(2, fee.getCollectivityId());
            stmt.setDate(3, Date.valueOf(fee.getEligibleFrom()));
            stmt.setString(4, fee.getFrequency());
            stmt.setBigDecimal(5, fee.getAmount());
            stmt.setString(6, fee.getLabel());
            stmt.setString(7, fee.getStatus());
            stmt.executeUpdate();
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) throws SQLException {
        List<MembershipFee> fees = new ArrayList<>();
        String sql = "SELECT * FROM membership_fees WHERE collectivity_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setCollectivityId(rs.getString("collectivity_id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(rs.getString("frequency"));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(rs.getString("status"));
                fees.add(fee);
            }
        }
        return fees;
    }

    public Optional<MembershipFee> findById(String id) throws SQLException {
        String sql = "SELECT * FROM membership_fees WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setCollectivityId(rs.getString("collectivity_id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(rs.getString("frequency"));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(rs.getString("status"));
                return Optional.of(fee);
            }
        }
        return Optional.empty();
    }
}