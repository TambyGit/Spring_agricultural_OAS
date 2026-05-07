package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CashAccount implements FinancialAccount {
    private String id;
    private Double amount;
}
