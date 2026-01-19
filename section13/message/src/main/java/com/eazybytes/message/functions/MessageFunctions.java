package com.eazybytes.message.functions;


import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eazybytes.message.dto.AccountsMsgDto;



@Configuration
public class MessageFunctions {
    
    private static final Logger log=LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email(){
        return accountsMsgDto ->{
            log.info("Sending email with the details: "+ accountsMsgDto.toString()); //we'll just print in log instead of sending emails
            return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto,Long> sms(){
        return accountsMsgDto ->{
            log.info("Sending sms with the details: "+ accountsMsgDto.toString()); //we'll just print in log instead of sending emails
            return accountsMsgDto.accountNumber(); //since the AccountsMsgDto is record type, its getter method is same as fieldname instead of prefix with get like other class
        };
    }
}
