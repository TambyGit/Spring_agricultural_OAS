package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.Gender;
import com.spring.tdfinaloas_collectivites_agricoles.model.enums.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

public class CreateMember {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private String phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private String collectivityIdentifier;
    private List<RefereeInput> referees;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
    private Double amount;
}
