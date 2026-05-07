package com.spring.tdfinaloas_collectivites_agricoles_finale.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAttendance {
    private String id;
    private Member member;
    private Activity activity;
    private AttendanceStatus status;
}