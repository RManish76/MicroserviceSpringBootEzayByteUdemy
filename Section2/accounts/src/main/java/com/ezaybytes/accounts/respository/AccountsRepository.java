package com.ezaybytes.accounts.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezaybytes.accounts.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long>{
    
}
