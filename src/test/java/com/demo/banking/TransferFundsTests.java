package com.demo.banking;

import com.demo.banking.dto.request.AccountRequest;
import com.demo.banking.dto.request.TransferRequest;
import com.demo.banking.dto.response.AccountResponse;
import com.demo.banking.dto.response.ErrorResponse;
import com.demo.banking.dto.response.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class TransferFundsTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "");
    }

    @Test
    void successTransferFundTest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(1234567890l);
        transferRequest.setTransferAmount(new BigDecimal(500));
        transferRequest.setSourceAccountNumber("BAK6756087363");
        transferRequest.setDestinationAccountNumber("BAK6756087364");
        // Initiate Transfer
        ResponseEntity<TransferResponse> responseResponseEntity = restTemplate
                .postForEntity(baseUrl.concat("/transfers"), transferRequest, TransferResponse.class);

        // Get Source account holder details
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setRequestId(1234567890l);
        accountRequest.setAccountNumber("BAK6756087363");
        ResponseEntity<AccountResponse> sourceAccount = restTemplate
                .postForEntity(baseUrl.concat("/accounts"), accountRequest, AccountResponse.class);


        // Get Destination account holder details
        AccountRequest accountRequest1 = new AccountRequest();
        accountRequest1.setRequestId(1234567890l);
        accountRequest1.setAccountNumber("BAK6756087364");
        ResponseEntity<AccountResponse> destinationAccountAccount = restTemplate
                .postForEntity(baseUrl.concat("/accounts"), accountRequest1, AccountResponse.class);

        assertAll(
                () -> assertNotNull(responseResponseEntity.getBody()),
                () -> assertEquals(200, responseResponseEntity.getStatusCodeValue()),
                //Validate if money deducted from source account
                () -> assertNotNull(sourceAccount.getBody()),
                () -> assertEquals(new BigDecimal("8486984.78"), sourceAccount.getBody().getAccountBalance()),

                // Validate if money credited to beneficiary account
                () -> assertNotNull(destinationAccountAccount.getBody()),
                () -> assertEquals(new BigDecimal("5455045.00"), destinationAccountAccount.getBody().getAccountBalance())
        );

    }

    @Test
    void sourceAndDestinationAccountIsSameFailureTest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(1234567890l);
        transferRequest.setTransferAmount(new BigDecimal(500));
        transferRequest.setSourceAccountNumber("BAK6756087365");
        transferRequest.setDestinationAccountNumber("BAK6756087365");

        ResponseEntity<ErrorResponse> responseResponseEntity = restTemplate
                .postForEntity(baseUrl.concat("/transfers"), transferRequest, ErrorResponse.class);

        assertAll(
                () -> assertNotNull(responseResponseEntity.getBody()),
                () -> assertEquals("INVALID REQUEST! SOURCE AND DESTINATION ACCOUNT CANNOT BE SAME.", responseResponseEntity.getBody().getMessage())
        );
    }

    @Test
    void sourceAccountNotFoundFailureTest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(1234567890l);
        transferRequest.setTransferAmount(new BigDecimal(500));
        transferRequest.setSourceAccountNumber("BAK6758087365");
        transferRequest.setDestinationAccountNumber("BAK6756087365");

        ResponseEntity<ErrorResponse> responseResponseEntity = restTemplate
                .postForEntity(baseUrl.concat("/transfers"), transferRequest, ErrorResponse.class);

        assertAll(
                () -> assertNotNull(responseResponseEntity.getBody()),
                () -> assertEquals("INVALID REQUEST! SOURCE ACCOUNT DETAILS NOT FOUND.", responseResponseEntity.getBody().getMessage())
        );
    }

    @Test
    void destinationAccountNotFoundFailureTest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(1234567890l);
        transferRequest.setTransferAmount(new BigDecimal(500));
        transferRequest.setSourceAccountNumber("BAK6756087365");
        transferRequest.setDestinationAccountNumber("BAK6758087305");

        ResponseEntity<ErrorResponse> responseResponseEntity = restTemplate
                .postForEntity(baseUrl.concat("/transfers"), transferRequest, ErrorResponse.class);

        assertAll(
                () -> assertNotNull(responseResponseEntity.getBody()),
                () -> assertEquals("INVALID REQUEST! DESTINATION ACCOUNT DETAILS NOT FOUND.", responseResponseEntity.getBody().getMessage())
        );
    }

    @Test
    void insufficientBalanceTest() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setRequestId(1234567890l);
        transferRequest.setTransferAmount(new BigDecimal(2202000));
        transferRequest.setSourceAccountNumber("BAK6756087365");
        transferRequest.setDestinationAccountNumber("BAK6758087365");
        ResponseEntity<ErrorResponse> responseResponseEntity = restTemplate
                .postForEntity(baseUrl.concat("/transfers"), transferRequest, ErrorResponse.class);
        assertAll(
                () -> assertNotNull(responseResponseEntity.getBody()),
                () -> assertEquals("INVALID REQUEST! DESTINATION ACCOUNT DETAILS NOT FOUND.", responseResponseEntity.getBody().getMessage())
        );
    }

}
