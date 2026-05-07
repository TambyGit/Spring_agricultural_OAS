package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    private String memberId;
    private String firstName;
    private String lastName;
    private String status; // "PRESENT" ou "ABSENT"
}