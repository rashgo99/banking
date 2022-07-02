package com.demo.banking.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse extends Response{
    private String accountNumber;
    private BigDecimal accountBalance;
}
