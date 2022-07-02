package com.demo.banking.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="ACCOUNT_DETAILS")
@Data
public class Account {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private BigDecimal accountBalance;

}
