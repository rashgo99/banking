package com.demo.banking.dto.request;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;

@Data
public class Request {
    @Digits(integer = 10,fraction = 0)
    @Positive
    private Long requestId;
}
