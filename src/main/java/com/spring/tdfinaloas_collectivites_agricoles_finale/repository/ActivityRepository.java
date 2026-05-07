package com.spring.tdfinaloas_collectivites_agricoles_finale.repository;

import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.Activity;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.ActivityAttendance;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.AttendanceStatus;
import com.spring.tdfinaloas_collectivites_agricoles_finale.mapper.ActivityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {
    private final Connection connection;
    private final ActivityMapper activityMapper;
    private final MemberRepository memberRepository;

    public List<Activity> saveAll(List<Activity> activities) {
        List<Activity> saved = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("""
        insert into activity (id, label, type, date, recurrence, collectivity_id)
        values (?, ?, ?::activity_type, ?, ?, ?)
        on conflict (id) do nothing
        """)) {
            for (Activity activity : activities) {
                ps.setString(1, activity.getId());
                ps.setString(2, activity.getLabel());
                ps.setString(3, activity.getType() == null ? null : activity.getType().name());
                ps.setDate(4, activity.getDate() == null ? null : Date.valueOf(activity.getDate()));
                ps.setString(5, activity.getRecurrence());
                ps.setString(6, activity.getCollectivity().getId());
                ps.addBatch();
            }
            ps.executeBatch();
            for (Activity activity : activities) {
                saved.add(findById(activity.getId()).orElseThrow());
            }
            return saved;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Activity> findById(String id) {
        try (PreparedStatement ps = connection.prepareStatement("""
                select id, label, date, collectivity_id from activity where id = ?
                """)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(activityMapper.mapFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Activity> findAllByCollectivityId(String collectivityId) {
        List<Activity> activities = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("""
                select id, label, type, date, recurrence, collectivity_id from activity where id = ?
                """)) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                activities.add(activityMapper.mapFromResultSet(rs));
            }
            return activities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActivityAttendance> saveAttendances(List<ActivityAttendance> attendances) {
        List<ActivityAttendance> saved = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("""
                insert into activity_attendance (id, activity_id, member_id, status)
                values (?, ?, ?, ?::attendance_status)
                on conflict (activity_id, member_id) do nothing
                """)) {
            for (ActivityAttendance a : attendances) {
                ps.setString(1, a.getId());
                ps.setString(2, a.getActivity().getId());
                ps.setString(3, a.getMember().getId());
                ps.setString(4, a.getStatus().name());
                ps.addBatch();
            }
            ps.executeBatch();
            return findAttendancesByActivityId(attendances.get(0).getActivity().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ActivityAttendance> findAttendancesByActivityId(String activityId) {
        List<ActivityAttendance> attendances = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("""
                select aa.id, aa.member_id, aa.status
                from activity_attendance aa
                where aa.activity_id = ?
                  and aa.status = 'PRESENT'
                """)) {
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ActivityAttendance attendance = ActivityAttendance.builder()
                        .id(rs.getString("id"))
                        .member(memberRepository.findById(rs.getString("member_id")).orElseThrow())
                        .status(AttendanceStatus.valueOf(rs.getString("status")))
                        .build();
                attendances.add(attendance);
            }
            return attendances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean attendanceAlreadyExists(String activityId, String memberId) {
        try (PreparedStatement ps = connection.prepareStatement("""
                select id from activity_attendance where activity_id = ? and member_id = ?
                """)) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long countActivitiesByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to) {
        try (PreparedStatement ps = connection.prepareStatement("""
            select count(*) from activity
            where collectivity_id = ? and date >= ? and date <= ?
            """)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long countPresencesByMemberAndPeriod(String memberId, String collectivityId, LocalDate from, LocalDate to) {
        try (PreparedStatement ps = connection.prepareStatement("""
            select count(*) from activity_attendance aa
            join activity a on aa.activity_id = a.id
            where aa.member_id = ?
              and a.collectivity_id = ?
              and a.date >= ? and a.date <= ?
              and aa.status = 'PRESENT'
            """)) {
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getLong(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}