package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateActivity {
    private String label;
    private String type;        // ← ajouter
    private String recurrence;  // ← ajouter
    private LocalDate date;
}