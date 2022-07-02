package com.demo.banking;

import com.demo.banking.entity.Account;
import com.demo.banking.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class BankingApplication {

    final AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            this.accountRepository.save(accountBiFunction.apply("BAK6756087365",new BigDecimal("220202.000")));
            this.accountRepository.save(accountBiFunction.apply("BAK6756087366",new BigDecimal("00.000")));
            this.accountRepository.save(accountBiFunction.apply("BAK6756087367",new BigDecimal("123.000")));
            this.accountRepository.save(accountBiFunction.apply("BAK6756087368",new BigDecimal("440404.000")));
            this.accountRepository.save(accountBiFunction.apply("BAK6756087364",new BigDecimal("5454545.000")));
            this.accountRepository.save(accountBiFunction.apply("BAK6756087363",new BigDecimal("8487484.777")));
            log.info("INSERTED ACCOUNTS SUCCESSFULLY!");
            log.trace("{}", this.accountRepository.findAll());
        };


    }

    private final BiFunction<String, BigDecimal, Account> accountBiFunction = (accountNumber, balance) ->{
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAccountBalance(balance);
		return account;
	};
}
