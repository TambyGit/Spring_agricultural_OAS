package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MemberPayment {
    private String id;
    private String memberId;
    private String membershipFeeId;
    private BigDecimal amount;
    private String paymentMode;
    private String accountCreditedId;
    private LocalDateTime paymentDate;
    private BigDecimal federationShare;
}