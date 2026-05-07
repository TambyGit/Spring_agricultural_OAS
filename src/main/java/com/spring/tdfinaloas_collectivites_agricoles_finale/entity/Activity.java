package com.spring.tdfinaloas_collectivites_agricoles_finale.entity;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    private String id;
    private String label;
    private ActivityType type;        // ← ajouter
    private LocalDate date;
    private String recurrence;        // ← ajouter
    private Collectivity collectivity;
    private List<ActivityAttendance> attendances;
}