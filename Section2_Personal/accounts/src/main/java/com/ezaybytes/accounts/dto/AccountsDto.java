package com.ezaybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
// import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(
    name = "Accounts",
    description = "Schema to hold Account information of Customer"
)
@Data //suggested by instructor that @Data is fine in dto class but not in entity class @Data might create issue due hashmethod and equal method of @Data
//so use @Getters @setter in entity class
public class AccountsDto {

    // @NotEmpty(message="AccountNumber can not be a null or empty")
    // @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number must be 10 digits")

    @Schema(
        description = "Account Number of Eazy Bank account", example = "98756841023"
    )
    @NotNull(message = "AccountNumber can not be a null or empty")
    @Min(value = 1000000000L, message = "Account number must be 10 digits")
    @Max(value = 9999999999L, message = "Account number must be 10 digits")
    private Long accountNumber;

 
    @Schema(
        description = "Account type of Eazy Bank account", example = "Savings"
    )
    @NotEmpty(message = "AccountType can not be a null or empty")
    private String accountType;


    @Schema(
        description = "Eazy Bank branch address", example = "NewYork 123"
    )
    @NotEmpty(message = "BranchAddress can not be a null or empty")
    private String branchAddress;
}
