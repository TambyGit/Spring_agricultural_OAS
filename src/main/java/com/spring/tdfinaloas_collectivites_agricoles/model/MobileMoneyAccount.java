package com.spring.tdfinaloas_collectivites_agricoles.model;

import com.spring.tdfinaloas_collectivites_agricoles.model.enums.MobileMoneyService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MobileMoneyAccount {
    private String id;
    private String holderName;
    private MobileMoneyService mobileService;
    private String phoneNumber;
    private BigDecimal balance;
    private String collectivityId;
    private LocalDateTime createdAt;

    public boolean isValidPhoneNumber() {
        return phoneNumber != null && phoneNumber.matches("(032|033|034|038|039)\\d{7}");
    }
}
