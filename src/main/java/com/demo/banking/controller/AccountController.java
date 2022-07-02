package com.demo.banking.controller;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.request.TransferRequest;
import com.demo.banking.dto.response.Response;
import com.demo.banking.facade.BankingFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("accounts")
@Slf4j
public class AccountController extends BaseController {


    public AccountController(BankingFacade<AccountRequest> accountInformationFacade, BankingFacade<TransferRequest> transferFundFacade) {
        super(accountInformationFacade, transferFundFacade);
    }

    @PostMapping
    public ResponseEntity<Response> getAccountDetails(@NotNull @Valid @RequestBody AccountRequest accountRequest) {
        log.info("[REQUEST-ID : {}] : ACCOUNT REQUEST RECEIVED.", accountRequest.getRequestId());
        return this.accountInformationFacade.processRequest(accountRequest);
    }
}
