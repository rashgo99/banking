package com.demo.banking.controller;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.request.TransferRequest;
import com.demo.banking.facade.BankingFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BaseController {

    final BankingFacade<AccountRequest> accountInformationFacade;
    final BankingFacade<TransferRequest> transferFundFacade;



}
