package com.spring.tdfinaloas_collectivites_agricoles_finale.repository;

import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.MemberPayment;
import com.spring.tdfinaloas_collectivites_agricoles_finale.mapper.MemberPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberPaymentRepository {
    private final Connection connection;
    private final MemberPaymentMapper memberPaymentMapper;

    public List<MemberPayment> saveAll(List<MemberPayment> memberPaymentList) {
        List<MemberPayment> memberPayments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                insert into member_payment (id, amount, creation_date, member_debited_id, membership_fee_id, payment_mode, financial_account_id)
                values (?, ?, ?, ?, ?, ?::payment_mode, ?)
                on conflict (id) do nothing
                """)) {
            for (MemberPayment memberPayment : memberPaymentList) {
                preparedStatement.setString(1, memberPayment.getId());
                preparedStatement.setDouble(2, memberPayment.getAmount());
                preparedStatement.setDate(3, Date.valueOf(memberPayment.getCreationDate()));
                preparedStatement.setString(4, memberPayment.getMemberOwner().getId());
                preparedStatement.setString(5, memberPayment.getMembershipFee().getId());
                preparedStatement.setString(6, memberPayment.getPaymentMode().name());
                preparedStatement.setString(7, memberPayment.getAccountCredited().getId());
                preparedStatement.addBatch();
            }
            var executedBatch = preparedStatement.executeBatch();
            for (int i = 0; i < executedBatch.length; i++) {
                MemberPayment memberPayment = memberPaymentList.get(i);
                memberPayments.add(findById(memberPayment.getId()).orElseThrow());
            }
            return memberPayments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<MemberPayment> findById(String id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                select id, amount, creation_date, member_debited_id, membership_fee_id, payment_mode, financial_account_id from member_payment
                where id = ?
                """)) {
            preparedStatement.setString(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return Optional.of(memberPaymentMapper.mapFromResultSet(resultSet));
            }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MemberPayment> findByMemberIdAndPeriod(String memberId, LocalDate from, LocalDate to) {
        List<MemberPayment> payments = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("""
            select id, amount, creation_date, member_debited_id, membership_fee_id, payment_mode, financial_account_id
            from member_payment
            where member_debited_id = ?
              and creation_date >= ?
              and creation_date <= ?
            """)) {
            ps.setString(1, memberId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(memberPaymentMapper.mapFromResultSet(rs));
            }
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
