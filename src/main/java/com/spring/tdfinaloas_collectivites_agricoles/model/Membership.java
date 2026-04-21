package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.time.LocalDate;
import java.util.Objects;

public class Membership {
    private Integer id;
    private String memberId;
    private String collectivityId;
    private LocalDate joinDate;

    public Membership() {
    }

    public Membership(Integer id, String memberId, String collectivityId, LocalDate joinDate) {
        this.id = id;
        this.memberId = memberId;
        this.collectivityId = collectivityId;
        this.joinDate = joinDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCollectivityId() {
        return collectivityId;
    }

    public void setCollectivityId(String collectivityId) {
        this.collectivityId = collectivityId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Membership that = (Membership) o;
        return Objects.equals(id, that.id) && Objects.equals(memberId, that.memberId) && Objects.equals(collectivityId, that.collectivityId) && Objects.equals(joinDate, that.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, collectivityId, joinDate);
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", collectivityId='" + collectivityId + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}
