package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Relationship;

import java.util.Objects;

public class Referee {
    private String memberId;
    private String refereeId;
    private Relationship relationship;
    private Member refereeDetails;

    public Referee() {
    }

    public Referee(String memberId, String refereeId, Relationship relationship, Member refereeDetails) {
        this.memberId = memberId;
        this.refereeId = refereeId;
        this.relationship = relationship;
        this.refereeDetails = refereeDetails;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Member getRefereeDetails() {
        return refereeDetails;
    }

    public void setRefereeDetails(Member refereeDetails) {
        this.refereeDetails = refereeDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referee referee = (Referee) o;
        return Objects.equals(memberId, referee.memberId) && Objects.equals(refereeId, referee.refereeId) && relationship == referee.relationship && Objects.equals(refereeDetails, referee.refereeDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, refereeId, relationship, refereeDetails);
    }

    @Override
    public String toString() {
        return "Referee{" +
                "memberId='" + memberId + '\'' +
                ", refereeId='" + refereeId + '\'' +
                ", relationship=" + relationship +
                ", refereeDetails=" + refereeDetails +
                '}';
    }
}
