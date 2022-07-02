package com.demo.banking.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class AccountRequest extends Request {
    @NotNull(message = "Account number cannot be null")
    @Length(min = 13, max = 13)
    private String accountNumber;

}
