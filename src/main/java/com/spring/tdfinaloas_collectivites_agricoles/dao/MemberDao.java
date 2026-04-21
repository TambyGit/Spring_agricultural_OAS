package com.spring.tdfinaloas_collectivites_agricoles.dao;

import com.spring.tdfinaloas_collectivites_agricoles.configuration.DataSource;
import com.spring.tdfinaloas_collectivites_agricoles.model.*;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Gender;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.MemberOccupation;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Relationship;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Repository
public class MemberDao {
    private final DataSource dataSource;

    public MemberDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Member member) throws SQLException {
        String sql = "INSERT INTO members (id, first_name, last_name, birth_date, gender, address, " +
                "profession, phone_number, email, occupation) VALUES (?, ?, ?, ?, ?::gender_enum, ?, ?, ?, ?, ?::occupation_enum)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getId());
            stmt.setString(2, member.getFirstName());
            stmt.setString(3, member.getLastName());
            stmt.setDate(4, Date.valueOf(member.getBirthDate()));
            stmt.setObject(5, member.getGender().name(), Types.OTHER);
            stmt.setString(6, member.getAddress());
            stmt.setString(7, member.getProfession());
            stmt.setString(8, member.getPhoneNumber());
            stmt.setString(9, member.getEmail());
            stmt.setObject(10, member.getOccupation().name(), Types.OTHER);
            stmt.executeUpdate();
        }
    }

    public void savePayment(Payment payment) throws SQLException {
        String sql = "INSERT INTO payments (member_id, collectivity_id, registration_fee_paid, membership_dues_paid, amount) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getMemberId());
            stmt.setString(2, payment.getCollectivityId());
            stmt.setBoolean(3, payment.getRegistrationFeePaid());
            stmt.setBoolean(4, payment.getMembershipDuesPaid());
            stmt.setDouble(5, payment.getAmount());
            stmt.executeUpdate();
        }
    }

    public void saveMembership(Membership membership) throws SQLException {
        String sql = "INSERT INTO memberships (member_id, collectivity_id, join_date) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, membership.getMemberId());
            stmt.setString(2, membership.getCollectivityId());
            stmt.setDate(3, Date.valueOf(membership.getJoinDate()));
            stmt.executeUpdate();
        }
    }

    public void saveReferee(Referee referee) throws SQLException {
        String sql = "INSERT INTO member_referees (member_id, referee_id, relationship) VALUES (?, ?, ?::relationship_enum)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, referee.getMemberId());
            stmt.setString(2, referee.getRefereeId());
            stmt.setObject(3, referee.getRelationship().name(), Types.OTHER);
            stmt.executeUpdate();
        }
    }

    public Optional<Member> findById(String id) throws SQLException {
        String sql = "SELECT * FROM members WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToMember(rs));
            }
            return Optional.empty();
        }
    }

    public List<Member> findByIds(List<String> ids) throws SQLException {
        if (ids == null || ids.isEmpty()) return new ArrayList<>();
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT * FROM members WHERE id IN (" + placeholders + ")";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < ids.size(); i++) {
                stmt.setString(i + 1, ids.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
            return members;
        }
    }

    public boolean existsById(String id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM members WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public List<Referee> findRefereesByMemberId(String memberId) throws SQLException {
        String sql = "SELECT * FROM member_referees WHERE member_id = ?";
        List<Referee> referees = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Referee referee = new Referee();
                referee.setMemberId(rs.getString("member_id"));
                referee.setRefereeId(rs.getString("referee_id"));
                referee.setRelationship(Relationship.valueOf(rs.getString("relationship")));
                referees.add(referee);
            }
        }
        return referees;
    }

    public Optional<Payment> findPaymentByMemberId(String memberId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE member_id = ? ORDER BY payment_date DESC LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setMemberId(rs.getString("member_id"));
                payment.setCollectivityId(rs.getString("collectivity_id"));
                payment.setRegistrationFeePaid(rs.getBoolean("registration_fee_paid"));
                payment.setMembershipDuesPaid(rs.getBoolean("membership_dues_paid"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                return Optional.of(payment);
            }
            return Optional.empty();
        }
    }

    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getString("id"));
        member.setFirstName(rs.getString("first_name"));
        member.setLastName(rs.getString("last_name"));
        member.setBirthDate(rs.getDate("birth_date").toLocalDate());
        member.setGender(Gender.valueOf(rs.getString("gender")));
        member.setAddress(rs.getString("address"));
        member.setProfession(rs.getString("profession"));
        member.setPhoneNumber(rs.getString("phone_number"));
        member.setEmail(rs.getString("email"));
        member.setOccupation(MemberOccupation.valueOf(rs.getString("occupation")));
        return member;
    }
}
