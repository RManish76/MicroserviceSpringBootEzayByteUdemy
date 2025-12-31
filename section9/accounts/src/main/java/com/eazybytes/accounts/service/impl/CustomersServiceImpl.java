package com.eazybytes.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CardsDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.LoansDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerDetailsMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomersService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService{

    //Since we have only once constructor which is AllArgsConsrutor, we don't need autorwire. It'll be done by spring automatically
    
    private AccountsRepository accountsRepository; //as we need to fetch account details of customer
    private CustomerRepository customerRepository; //as we need to fetch customer details of cusomer.

    private CardsFeignClient cardsFeignClient; //as we need to fethc cards details of customer and we have to use feign class for it
    private LoansFeignClient loansFeignClient; //as we need to fethc loans details of customer and we have to use feign class for it


    /**
     * 
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a give mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetailsDto(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerDetailsMapper.mapToCustomerDetailDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        //loans and cardr realted dto need to set in customerDetailsDto variable

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        //above like will trigger the feign client, which will get the details form loansMS.
        //the reponse which we'll get from feign client will of type ResponseEntity.
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        //above like will trigger the feign client, which will get the details form loansMS.
        //the reponse which we'll get from feign client will of type ResponseEntity.
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());


        return customerDetailsDto;
    }



    
}
