package com.ezaybytes.accounts.mapper;

import com.ezaybytes.accounts.dto.AccountsDto;
import com.ezaybytes.accounts.entity.Accounts;

//we have libraries available online like modelmapper, mapstruct to this automatic mapping
public class AccountsMapper {
    //this is responsbile to map entity to DTO and vice-versa

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto){
        //mapping data from acounts class/entity/table to acountsDto to return
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts){
        //mapping data from accountsDto to account/entity/table class to store it in table
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
