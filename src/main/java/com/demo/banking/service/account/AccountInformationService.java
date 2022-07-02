package com.demo.banking.service.account;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.response.Response;
import com.demo.banking.entity.Account;
import com.demo.banking.repository.AccountRepository;
import com.demo.banking.repository.TransactionRepository;
import com.demo.banking.service.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountInformationService extends BaseService {

    public AccountInformationService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        super(transactionRepository, accountRepository);
    }

    public ResponseEntity<Response> processRequest(AccountRequest accountRequest) {


        Optional<Account> optionalAccount = this.accountRepository.findByAccountNumber(accountRequest.getAccountNumber());

        if (optionalAccount.isPresent()) {
            return accountRequestAccountResponseBiFunction.apply(optionalAccount.get(), accountRequest);
        } else {
            return errorResponseFunction.apply(accountRequest,"ACCOUNT DETAILS NOT FOUND!");
        }


    }


}
