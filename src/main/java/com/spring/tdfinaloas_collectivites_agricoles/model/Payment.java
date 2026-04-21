package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.time.LocalDateTime;

public class Payment {
    private Integer id;
    private String memberId;
    private String collectivityId;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
    private Double amount;
    private LocalDateTime paymentDate;
}
