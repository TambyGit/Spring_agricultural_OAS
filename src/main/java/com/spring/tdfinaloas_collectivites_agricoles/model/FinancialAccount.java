package com.spring.tdfinaloas_collectivites_agricoles.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinancialAccount {
    protected String id;
    protected String holderName;
    protected BigDecimal balance;
    protected String collectivityId;
    protected LocalDateTime createdAt;
}
