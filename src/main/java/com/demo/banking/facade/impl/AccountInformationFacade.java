package com.demo.banking.facade.impl;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.response.Response;
import com.demo.banking.facade.BankingFacade;
import com.demo.banking.service.account.AccountInformationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
@Component
@AllArgsConstructor
public class AccountInformationFacade implements BankingFacade<AccountRequest> {

    final AccountInformationService accountInformationService;

    @Override
    public ResponseEntity<Response> processRequest(AccountRequest accountRequest) {
        return this.accountInformationService.processRequest(accountRequest);

    }
}
