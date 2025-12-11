package com.ezaybytes.accounts.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezaybytes.accounts.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{

}
