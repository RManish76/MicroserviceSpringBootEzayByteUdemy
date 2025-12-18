package com.ezaybytes.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ErrorResponseDto {
    
    //this class will be responsible for sending error msg for rest api calls

    private String apiPath; //we want send the api path as well with error msg in response
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime errorTime;
}
