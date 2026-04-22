package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private String collectivityId;
    private String memberId;
    private BigDecimal amount;
    private String paymentMode;
    private String accountCreditedId;
    private LocalDateTime transactionDate;
    private String type;
}
