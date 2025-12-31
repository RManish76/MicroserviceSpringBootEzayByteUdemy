package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {
    /**
     * 
     * @param mobileNumber - Input Mobile Number of Customer
     * @return Customer Details based on a given mobileNumber
     */
    CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber);
}
