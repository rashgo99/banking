package com.demo.banking.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TransferRequest extends Request{
    @NotNull(message = "Source account number cannot be null")
    @Length(min = 13,max = 13)
    private String sourceAccountNumber;

    @NotNull(message = "Destination account number cannot be null")
    @Length(min = 13,max = 13)
    private String destinationAccountNumber;

    @NotNull(message = "Transfer account number cannot be null")
    @Positive
    @Digits(integer = 15, fraction = 4)
    private BigDecimal transferAmount;

}
