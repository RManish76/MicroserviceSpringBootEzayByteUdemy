package com.ezaybytes.accounts.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezaybytes.accounts.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long>{
    
    Optional<Accounts> findByCustomerId(Long customerId);
}
