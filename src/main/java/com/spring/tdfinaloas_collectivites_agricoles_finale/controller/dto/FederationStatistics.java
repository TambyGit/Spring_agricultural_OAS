package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FederationStatistics {
    private String collectivityId;
    private String collectivityName;
    private LocalDate from;
    private LocalDate to;
    private Double upToDateMembersPercentage;
    private Integer newMembersCount;
    private Double globalAttendanceRate;
}