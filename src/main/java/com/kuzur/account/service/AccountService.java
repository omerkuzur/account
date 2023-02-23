package com.kuzur.account.service;

import com.kuzur.account.dto.AccountDto;
import com.kuzur.account.dto.AccountDtoConverter;
import com.kuzur.account.dto.CreateAccountRequest;
import com.kuzur.account.model.Account;
import com.kuzur.account.model.Customer;
import com.kuzur.account.model.Transaction;
import com.kuzur.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
    }


    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findByCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now());
        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);
            account.getTransaction().add(transaction);
        }


        return converter.Convert(accountRepository.save(account));
        }





}