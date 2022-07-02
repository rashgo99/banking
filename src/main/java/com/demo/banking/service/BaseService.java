package com.demo.banking.service;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.request.Request;
import com.demo.banking.dto.response.AccountResponse;
import com.demo.banking.dto.response.ErrorResponse;
import com.demo.banking.dto.response.Response;
import com.demo.banking.dto.response.TransferResponse;
import com.demo.banking.entity.Account;
import com.demo.banking.entity.Transaction;
import com.demo.banking.repository.AccountRepository;
import com.demo.banking.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.demo.banking.enumeration.Status.FAILED;
import static com.demo.banking.enumeration.Status.SUCCESS;

@Slf4j
@AllArgsConstructor
public abstract class BaseService {

    protected final TransactionRepository transactionRepository;
    protected final AccountRepository accountRepository;

    protected final BiFunction<Account, AccountRequest, ResponseEntity<Response>> accountRequestAccountResponseBiFunction = (account, accountRequest) -> {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setRequestId(accountRequest.getRequestId());
        accountResponse.setStatus(SUCCESS);
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setAccountBalance(account.getAccountBalance());
        return ResponseEntity.ok(accountResponse);
    };

    protected final BiFunction<Request, String, ResponseEntity<Response>> errorResponseFunction = (request, message) -> {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setRequestId(request.getRequestId());
        errorResponse.setStatus(FAILED);
        errorResponse.setMessage(message);
        return ResponseEntity.ok(errorResponse);
    };

    protected final Function<Transaction, ResponseEntity<Response>> transactionResponseEntityFunction = (transaction) -> {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setRequestId(transaction.getRequestId());
        transferResponse.setTransferAmount(transaction.getTransferAmount());
        transferResponse.setStatus(SUCCESS);
        transferResponse.setTransactionReferenceNumber(transaction.getTransactionReferenceNumber());
        transferResponse.setTransactionTimestamp(transaction.getTransactionTimestamp());
        return ResponseEntity.ok(transferResponse);
    };

}
