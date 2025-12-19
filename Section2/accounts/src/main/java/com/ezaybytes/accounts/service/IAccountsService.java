package com.ezaybytes.accounts.service;

import com.ezaybytes.accounts.dto.CustomerDto;

public interface IAccountsService {
    
    /**
     * 
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a given mobileNumber
     */
    CustomerDto fetchAccount(String mobilenumber);
}
