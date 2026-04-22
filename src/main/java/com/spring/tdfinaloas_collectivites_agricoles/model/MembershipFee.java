package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Frequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MembershipFee {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private Frequency frequency;  // ENUM
    private BigDecimal amount;
    private String label;
    private ActivityStatus status;
}
