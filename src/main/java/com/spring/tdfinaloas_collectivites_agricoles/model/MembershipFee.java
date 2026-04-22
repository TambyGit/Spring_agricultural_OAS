package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.ActivityStatus;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Frequency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class MembershipFee {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private BigDecimal amount;
    private String label;
    private ActivityStatus status;

    public MembershipFee() {
    }
    public MembershipFee(String id, String collectivityId, LocalDate eligibleFrom, Frequency frequency, BigDecimal amount, String label, ActivityStatus status) {
        this.id = id;
        this.collectivityId = collectivityId;
        this.eligibleFrom = eligibleFrom;
        this.frequency = frequency;
        this.amount = amount;
        this.label = label;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
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

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipFee that = (MembershipFee) o;
        return Objects.equals(id, that.id) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(eligibleFrom, that.eligibleFrom) && frequency == that.frequency && Objects.equals(amount, that.amount) && Objects.equals(label, that.label) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, collectivityId, eligibleFrom, frequency, amount, label, status);
    }

    @Override
    public String toString() {
        return "MembershipFee{" +
                "id='" + id + '\'' +
                ", collectivityId='" + collectivityId + '\'' +
                ", eligibleFrom=" + eligibleFrom +
                ", frequency=" + frequency +
                ", amount=" + amount +
                ", label='" + label + '\'' +
                ", status=" + status +
                '}';
    }
}
