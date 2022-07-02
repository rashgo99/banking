package com.demo.banking.facade.impl;

import com.demo.banking.dto.request.TransferRequest;
import com.demo.banking.dto.response.Response;
import com.demo.banking.facade.BankingFacade;
import com.demo.banking.service.transfer.TransferFundService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransferFundFacade implements BankingFacade<TransferRequest> {
final TransferFundService transferFundService;
    @Override
    public ResponseEntity<Response> processRequest(TransferRequest transferRequest) {
        return this.transferFundService.processRequest(transferRequest);

    }
}
