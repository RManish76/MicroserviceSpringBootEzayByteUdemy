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

    /**
     * @param customerDto - CustomerDto Object
     * @return Bolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}
