package com.ezaybytes.accounts.exception;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ezaybytes.accounts.dto.ErrorResponseDto;

@ControllerAdvice //Any exceptiion occured for any controller should come here
public class GlobalExceptionHandler {
    

    @ExceptionHandler(Exception.class) //This exception will excecute only when spring will not able to find customeDefined exception which occured
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                        WebRequest webRequest){
        //the WebRequest store metadata information related request like api link, ipAddress etc.
        //we have ErroResponseDto which accept 4 field and we'll send those as response
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
            webRequest.getDescription(false), //setting false will give only api path link & setting true will give a lot of infor like ipAddres
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.getMessage(),
            LocalDateTime.now()    
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
                                                                            
    }


    @ExceptionHandler(CustomerAlreadyExistException.class) //tells to spring for which exceptiion below method is for handling
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistException exception,
                                                                        WebRequest webRequest){
        //the WebRequest store metadata information related request like api link, ipAddress etc.
        //we have ErroResponseDto which accept 4 field and we'll send those as response
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
            webRequest.getDescription(false), //setting false will give only api path link & setting true will give a lot of infor like ipAddres
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            LocalDateTime.now()    
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
                                                                            
    }

    @ExceptionHandler(ResourceNotFoundException.class) //tells to spring for which exceptiion below method is for handling
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        //the WebRequest store metadata information related request like api link, ipAddress etc.
        //we have ErroResponseDto which accept 4 field and we'll send those as response
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
            webRequest.getDescription(false), //setting false will give only api path link & setting true will give a lot of infor like ipAddres
            HttpStatus.NOT_FOUND,
            exception.getMessage(),
            LocalDateTime.now()    
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
                                                                            
    }
}
