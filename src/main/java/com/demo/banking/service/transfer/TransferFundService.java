package com.demo.banking.service.transfer;

import com.demo.banking.dto.request.TransferRequest;
import com.demo.banking.dto.response.Response;
import com.demo.banking.entity.Account;
import com.demo.banking.entity.Transaction;
import com.demo.banking.repository.AccountRepository;
import com.demo.banking.repository.TransactionRepository;
import com.demo.banking.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class TransferFundService extends BaseService {

    public TransferFundService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        super(transactionRepository, accountRepository);
    }

    @Transactional
    public ResponseEntity<Response> processRequest(TransferRequest transferRequest) {

        if(transferRequest.getSourceAccountNumber().equals(transferRequest.getDestinationAccountNumber()))
            return errorResponseFunction.apply(transferRequest, "INVALID REQUEST! SOURCE AND DESTINATION ACCOUNT CANNOT BE SAME.");

        Optional<Account> optionalSourceAccount = this.accountRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        Optional<Account> optionalDestinationAccount = this.accountRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());

        if (!optionalSourceAccount.isPresent())
            return errorResponseFunction.apply(transferRequest, "INVALID REQUEST! SOURCE ACCOUNT DETAILS NOT FOUND.");
        if (!optionalDestinationAccount.isPresent())
            return errorResponseFunction.apply(transferRequest, "INVALID REQUEST! DESTINATION ACCOUNT DETAILS NOT FOUND.");
        if (transferAmountIsNotValid(optionalSourceAccount.get(), transferRequest))
            return errorResponseFunction.apply(transferRequest, "INVALID REQUEST! INSUFFICIENT FUNDS.");

        Account sourceAccount = optionalSourceAccount.get();
        Account destinationAccount = optionalDestinationAccount.get();
        sourceAccount.setAccountBalance(optionalSourceAccount.get().getAccountBalance().subtract(transferRequest.getTransferAmount()));
        destinationAccount.setAccountBalance(optionalDestinationAccount.get().getAccountBalance().add(transferRequest.getTransferAmount()));
        Transaction transaction = getTransaction(sourceAccount, destinationAccount, transferRequest);
        this.accountRepository.save(sourceAccount);
        this.accountRepository.save(destinationAccount);
        this.transactionRepository.save(transaction);
        return transactionResponseEntityFunction.apply(transaction);

    }

    private boolean transferAmountIsNotValid(Account account, TransferRequest transferRequest) {
        return transferRequest.getTransferAmount().compareTo(account.getAccountBalance()) > 0;
    }

    private Transaction getTransaction(Account sourceAccount, Account destinationAccount, TransferRequest transferRequest) {
        return Transaction.builder()
                .requestId(transferRequest.getRequestId())
                .sourceAccount(sourceAccount)
                .destinationAccountNumber(destinationAccount)
                .transferAmount(transferRequest.getTransferAmount())
                .transactionReferenceNumber("TRN-".concat(String.valueOf(System.currentTimeMillis())))
                .transactionTimestamp(now())
                .build();
    }
}
