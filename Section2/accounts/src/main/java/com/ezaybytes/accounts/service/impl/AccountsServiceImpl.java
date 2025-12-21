package com.ezaybytes.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.ezaybytes.accounts.constants.AccountsConstants;
import com.ezaybytes.accounts.dto.AccountsDto;
import com.ezaybytes.accounts.dto.CustomerDto;
import com.ezaybytes.accounts.entity.Accounts;
import com.ezaybytes.accounts.entity.Customer;
import com.ezaybytes.accounts.exception.CustomerAlreadyExistException;
import com.ezaybytes.accounts.exception.ResourceNotFoundException;
import com.ezaybytes.accounts.mapper.AccountsMapper;
import com.ezaybytes.accounts.mapper.CustomerMapper;
import com.ezaybytes.accounts.respository.AccountsRepository;
import com.ezaybytes.accounts.respository.CustomerRepository;
import com.ezaybytes.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{

    /**
     * We don't need autowire here.
     * Whenever we have only one constructer in a class, And that single constructor accept all args
     * springboot can automatically autowire it
     */
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * 
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Optional<Customer> optionalCustomer = customerRepository
                                        .findByMobileNumber(customerDto.getMobileNumber());
                                
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber "
                +customerDto.getMobileNumber());
        }

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        // customer.setCreatedAt(LocalDateTime.now());
        // customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
        
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100_000_0000L + new Random().nextInt(900_000_000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        // newAccount.setCreatedAt(LocalDateTime.now());
        // newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
            () -> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );

        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;        
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return Bolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                ()-> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);

            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
            ()->new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


    

    
    
}
