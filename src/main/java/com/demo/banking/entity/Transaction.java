package com.demo.banking.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@Data
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long requestId;

    private String transactionReferenceNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sourceAccount", referencedColumnName = "accountNumber")
    private Account sourceAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destinationAccountNumber", referencedColumnName = "accountNumber")
    private Account destinationAccountNumber;

    @Digits(integer = 15, fraction = 4)
    @Min(value = 1)
    @Positive
    private BigDecimal transferAmount;

    private LocalDateTime transactionTimestamp;
}
