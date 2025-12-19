package com.ezaybytes.accounts.dto;

import lombok.Data;

@Data
public class CustomerDto {
    private String name;
    private String email;
    private String mobileNumber;    

    //added to send respone to customer for get request. Can create seprate DTO as well if to many fields are there
    private AccountsDto accountsDto;
}
