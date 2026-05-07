package com.spring.tdfinaloas_collectivites_agricoles_finale.mapper;

import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.Activity;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.ActivityType;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActivityMapper {
    public Activity mapFromResultSet(ResultSet rs) {
        try {
            return Activity.builder()
                    .id(rs.getString("id"))
                    .label(rs.getString("label"))
                    .type(rs.getString("type") == null ? null : ActivityType.valueOf(rs.getString("type")))
                    .recurrence(rs.getString("recurrence"))
                    .date(rs.getDate("date") == null ? null : rs.getDate("date").toLocalDate())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
