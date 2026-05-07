package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttendance {
    private List<String> presentMemberIds;
    private List<String> absentMemberIds;
}