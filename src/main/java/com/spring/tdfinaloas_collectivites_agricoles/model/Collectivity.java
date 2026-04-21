package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.util.List;
import java.util.Objects;

public class Collectivity {
    private String id;
    private String location;
    private Boolean federationApproval;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {
    }

    public Collectivity(String id, String location, Boolean federationApproval, CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.location = location;
        this.federationApproval = federationApproval;
        this.structure = structure;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(Boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public CollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CollectivityStructure structure) {
        this.structure = structure;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collectivity that = (Collectivity) o;
        return Objects.equals(id, that.id) && Objects.equals(location, that.location) && Objects.equals(federationApproval, that.federationApproval) && Objects.equals(structure, that.structure) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, federationApproval, structure, members);
    }

    @Override
    public String toString() {
        return "Collectivity{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", federationApproval=" + federationApproval +
                ", structure=" + structure +
                ", members=" + members +
                '}';
    }
}
