package com.ezaybytes.accounts.service;

import com.ezaybytes.accounts.dto.CustomerDto;

public interface IAccountsService {
    
    /**
     * 
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);
}
