package com.demo.banking.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferResponse extends Response{

    private String transactionReferenceNumber;
    private BigDecimal transferAmount;
    private LocalDateTime transactionTimestamp;
}
