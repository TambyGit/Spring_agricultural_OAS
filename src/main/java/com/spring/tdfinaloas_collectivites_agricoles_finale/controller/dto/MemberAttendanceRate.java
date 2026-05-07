package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAttendanceRate {
    private String memberId;
    private String firstName;
    private String lastName;
    private Double attendanceRate; // % de présence sur la période
}