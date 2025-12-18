package com.ezaybytes.accounts.service.impl;

import org.springframework.stereotype.Service;

import com.ezaybytes.accounts.dto.CustomerDto;
import com.ezaybytes.accounts.respository.AccountsRepository;
import com.ezaybytes.accounts.respository.CustomerRepository;
import com.ezaybytes.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{

    /**
     * We don't need autowire here.
     * Whenever we have only one constructer in a class, And that single constructor accept all args
     * springboot can automatically autowire it
     */
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * 
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        
        
    }
    
}
