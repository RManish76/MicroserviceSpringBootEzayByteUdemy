package com.ezaybytes.accounts.mapper;

import com.ezaybytes.accounts.dto.CustomerDto;
import com.ezaybytes.accounts.entity.Customer;

//we have libraries available online like modelmapper, mapstruct to this automatic mapping
public class CustomerMapper {
    
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto){
        //mapping customer table/entity/class object data to customerDto class
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer){
        // mapping customerDto data to coustomer classs/entity/table
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;

    }
}
