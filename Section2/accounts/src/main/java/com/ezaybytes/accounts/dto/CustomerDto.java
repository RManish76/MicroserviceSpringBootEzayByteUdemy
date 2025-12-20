package com.ezaybytes.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be a null or empty") //tells to spring validatiion it cannot be empty. If empty recived return the messsage
    @Size(min = 5, max = 30, message = "The lenth of the customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a vaild value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;    

    //added to send respone to customer for get request. Can create seprate DTO as well if to many fields are there
    private AccountsDto accountsDto;
}
