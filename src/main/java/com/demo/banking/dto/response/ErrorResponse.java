package com.demo.banking.dto.response;

import lombok.Data;

@Data
public class ErrorResponse extends Response{
    private String message;
}