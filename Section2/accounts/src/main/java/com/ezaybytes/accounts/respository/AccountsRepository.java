package com.ezaybytes.accounts.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.ezaybytes.accounts.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long>{
    
    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional //below query/method should be Transactional ie it should be able to roleback automatically if failuer occured in middle of execution
    @Modifying  //tells spring below query/method will modify the data
    void deleteByCustomerId(Long customerId);
}
