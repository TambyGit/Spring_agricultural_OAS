package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Frequency;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateMembershipFee {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private BigDecimal amount;
    private String label;

    public CreateMembershipFee() {
    }

    public CreateMembershipFee(LocalDate eligibleFrom, Frequency frequency, BigDecimal amount, String label) {
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
    }

    public LocalDate getEligibleFrom() {
        return eligibleFrom;
    }

    public void setEligibleFrom(LocalDate eligibleFrom) {
        this.eligibleFrom = eligibleFrom;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}