package com.demo.banking.facade;

import com.demo.banking.dto.response.Response;
import org.springframework.http.ResponseEntity;


@FunctionalInterface
public interface BankingFacade<T> {
    ResponseEntity<Response> processRequest(T t);
}
