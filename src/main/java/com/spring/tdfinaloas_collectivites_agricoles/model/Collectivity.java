package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.util.List;
import java.util.Objects;

public class Collectivity {
    private String id;
    private String location;
    private String number;
    private String name;
    private Boolean federationApproval;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {
    }

    public Collectivity(String id, String location, String number, String name, Boolean federationApproval, 
                        CollectivityStructure structure, List<Member> members) {
        this.id = id;
        this.location = location;
        this.number = number;
        this.name = name;
        this.federationApproval = federationApproval;
        this.structure = structure;
        this.members = members;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getFederationApproval() { return federationApproval; }
    public void setFederationApproval(Boolean federationApproval) { this.federationApproval = federationApproval; }

    public CollectivityStructure getStructure() { return structure; }
    public void setStructure(CollectivityStructure structure) { this.structure = structure; }

    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }

    public boolean hasAttribution() {
        return this.number != null && this.name != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collectivity that = (Collectivity) o;
        return Objects.equals(id, that.id) && Objects.equals(location, that.location) 
                && Objects.equals(number, that.number) && Objects.equals(name, that.name)
                && Objects.equals(federationApproval, that.federationApproval) 
                && Objects.equals(structure, that.structure) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, number, name, federationApproval, structure, members);
    }

    @Override
    public String toString() {
        return "Collectivity{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", federationApproval=" + federationApproval +
                ", structure=" + structure +
                ", members=" + members +
                '}';
    }
}