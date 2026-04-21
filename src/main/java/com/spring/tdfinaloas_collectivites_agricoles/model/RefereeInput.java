package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Relationship;

import java.util.Objects;

public class RefereeInput {
    private String refereeId;
    private Relationship relationship;

    public RefereeInput(String refereeId, Relationship relationship) {
        this.refereeId = refereeId;
        this.relationship = relationship;
    }

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefereeInput that = (RefereeInput) o;
        return Objects.equals(refereeId, that.refereeId) && relationship == that.relationship;
    }

    @Override
    public int hashCode() {
        return Objects.hash(refereeId, relationship);
    }

    @Override
    public String toString() {
        return "RefereeInput{" +
                "refereeId='" + refereeId + '\'' +
                ", relationship=" + relationship +
                '}';
    }
}
