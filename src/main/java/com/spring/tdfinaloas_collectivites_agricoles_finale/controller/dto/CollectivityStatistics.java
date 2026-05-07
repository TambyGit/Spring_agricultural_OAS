package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityStatistics {
    private LocalDate from;
    private LocalDate to;
    private Double totalCollectedAmount;
    private Double potentialUnpaidAmountPerMember;
    private List<MemberAttendanceRate> memberAttendanceRates;
}