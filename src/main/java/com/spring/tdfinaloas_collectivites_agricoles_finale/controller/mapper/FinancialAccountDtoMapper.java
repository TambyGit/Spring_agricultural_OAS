package com.spring.tdfinaloas_collectivites_agricoles_finale.controller.mapper;

import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.Bank;
import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.FinancialAccount;
import com.spring.tdfinaloas_collectivites_agricoles_finale.controller.dto.MobileBankingService;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.BankAccount;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.CashAccount;
import com.spring.tdfinaloas_collectivites_agricoles_finale.entity.MobileBankingAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Component
public class FinancialAccountDtoMapper {
    public FinancialAccount mapToDto(FinancialAccount financialAccount, LocalDate at) {
        LocalDate balanceAt = at == null ? now() : at;
        if (financialAccount instanceof CashAccount cashAccount) {
            return CashAccount.builder()
                    .id(cashAccount.getId())
                    .amount(cashAccount.getBalanceAt(balanceAt))
                    .build();
        } else if (financialAccount instanceof BankAccount bankAccount) {
            return BankAccount.builder()
                    .id(bankAccount.getId())
                    .holderName(bankAccount.getHolderName())
                    .bankName(bankAccount.getBankName() == null ? null : Bank.valueOf(bankAccount.getBankName().name()))
                    .bankCode(bankAccount.getBankCode())
                    .bankBranchCode(bankAccount.getBranchCode())
                    .bankAccountNumber(bankAccount.getAccountNumber())
                    .bankAccountKey(bankAccount.getAccountKey())
                    .amount(bankAccount.getBalanceAt(balanceAt))
                    .build();
        } else if (financialAccount instanceof MobileBankingAccount mobileBankingAccount) {
            return MobileBankingAccount.builder()
                    .id(mobileBankingAccount.getId())
                    .holderName(mobileBankingAccount.getHolderName())
                    .mobileNumber(mobileBankingAccount.getMobileNumber())
                    .mobileBankingService(mobileBankingAccount.getMobileBankingService() == null ? null : MobileBankingService.valueOf(mobileBankingAccount.getMobileBankingService().name()))
                    .amount(mobileBankingAccount.getBalanceAt(balanceAt))
                    .build();
        }
        throw new IllegalArgumentException("Unknown financial account type " + financialAccount.getClass().getName());
    }

    public FinancialAccount mapToDto(FinancialAccount financialAccount) {
        return  mapToDto(financialAccount, now());
    }

}
