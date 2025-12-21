package com.ezaybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
    name = "Customer",
    description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
        description = "Name of the customer", example = "Eazy Bytes"
    )
    @NotEmpty(message = "Name can not be a null or empty") //tells to spring validatiion it cannot be empty. If empty recived return the messsage
    @Size(min = 5, max = 30, message = "The lenth of the customer name should be between 5 and 30")
    private String name;

    @Schema(
        description = "Email of customer", example = "user@example.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a vaild value")
    private String email;


    @Schema(
        description = "Mobile number of customer", example = "9321450876"
    )
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;    


    @Schema(
        description = "Account details of the Customer"
    )
    //added to send respone to customer for get request. Can create seprate DTO as well if to many fields are there
    @Valid
    private AccountsDto accountsDto;
}
