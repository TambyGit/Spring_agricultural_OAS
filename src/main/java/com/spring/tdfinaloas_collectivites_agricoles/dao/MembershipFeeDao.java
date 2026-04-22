package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.CustomDataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.ActivityStatus;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Frequency;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class MembershipFeeDao {
    private final CustomDataSource customDataSource;

    public MembershipFeeDao(CustomDataSource customDataSource) {
        this.customDataSource = customDataSource;
    }

    public void save(MembershipFee fee) throws SQLException {
        String sql = "INSERT INTO membership_fees (id, collectivity_id, eligible_from, frequency, amount, label, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fee.getId());
            stmt.setString(2, fee.getCollectivityId());
            stmt.setDate(3, Date.valueOf(fee.getEligibleFrom()));
            stmt.setString(4, fee.getFrequency().name());
            stmt.setBigDecimal(5, fee.getAmount());
            stmt.setString(6, fee.getLabel());
            stmt.setString(7, fee.getStatus().name());
            stmt.executeUpdate();
        }
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) throws SQLException {
        List<MembershipFee> fees = new ArrayList<>();
        String sql = "SELECT * FROM membership_fees WHERE collectivity_id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, collectivityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setCollectivityId(rs.getString("collectivity_id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                fees.add(fee);
            }
        }
        return fees;
    }

    public Optional<MembershipFee> findById(String id) throws SQLException {
        String sql = "SELECT * FROM membership_fees WHERE id = ?";
        try (Connection conn = (Connection) customDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MembershipFee fee = new MembershipFee();
                fee.setId(rs.getString("id"));
                fee.setCollectivityId(rs.getString("collectivity_id"));
                fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                fee.setAmount(rs.getBigDecimal("amount"));
                fee.setLabel(rs.getString("label"));
                fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
                return Optional.of(fee);
            }
        }
        return Optional.empty();
    }
}