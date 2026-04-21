package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.util.List;
import java.util.Objects;

public class CreateCollectivity {
    private String location;
    private List<String> members;
    private Boolean federationApproval;
    private CreateCollectivityStructure structure;

    public CreateCollectivity(String location, List<String> members, Boolean federationApproval, CreateCollectivityStructure structure) {
        this.location = location;
        this.members = members;
        this.federationApproval = federationApproval;
        this.structure = structure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Boolean getFederationApproval() {
        return federationApproval;
    }

    public void setFederationApproval(Boolean federationApproval) {
        this.federationApproval = federationApproval;
    }

    public CreateCollectivityStructure getStructure() {
        return structure;
    }

    public void setStructure(CreateCollectivityStructure structure) {
        this.structure = structure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateCollectivity that = (CreateCollectivity) o;
        return Objects.equals(location, that.location) && Objects.equals(members, that.members) && Objects.equals(federationApproval, that.federationApproval) && Objects.equals(structure, that.structure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, members, federationApproval, structure);
    }

    @Override
    public String
    toString() {
        return "CreateCollectivity{" +
                "location='" + location + '\'' +
                ", members=" + members +
                ", federationApproval=" + federationApproval +
                ", structure=" + structure +
                '}';
    }
}
