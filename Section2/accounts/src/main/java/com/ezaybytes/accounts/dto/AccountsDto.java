package com.ezaybytes.accounts.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data //suggested by instructor that @Data is fine in dto class but not in entity class @Data might create issue due hashmethod and equal method of @Data
//so use @Getters @setter in entity class
public class AccountsDto {

    // @NotEmpty(message="AccountNumber can not be a null or empty")
    // @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")

    @NotNull(message = "AccountNumber can not be a null or empty")
    @Min(value = 1000000000L, message = "Account number must be 10 digits")
    @Max(value = 9999999999L, message = "Account number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;

    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
