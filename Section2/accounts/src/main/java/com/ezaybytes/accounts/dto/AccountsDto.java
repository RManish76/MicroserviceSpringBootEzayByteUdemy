package com.ezaybytes.accounts.dto;


import lombok.Data;

@Data //suggested by instructor that @Data is fine in dto class but not in entity class @Data might create issue due hashmethod and equal method of @Data
//so use @Getters @setter in entity class
public class AccountsDto {

    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
