package com.ezaybytes.accounts.exception;


import org.springframework.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ezaybytes.accounts.dto.ErrorResponseDto;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice //Any exceptiion occured for any controller should come here
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //below commented method fount in Q&A and below method was for that, i was
    //looking for some validaiton problem found below but did not work for me
    /**
     * That person stated issue like below 
     * a non valid RequestParam should produce an Http Status code BAD_REQUEST(400) This never occurs but instead
     * produces an Http Status Code 500. This is due to the fact that a ConstraintViolationException is thrown not a
     * MethodArgumentNotValidException as expeted and this triggers the general Exception Handler. I added a new
     * one and now it works properly. Following the new |Exception Handler
     */
    // @ExceptionHandler(ConstraintViolationException.class)
    // public ResponseEntity<Object> handleConstraintViolations(
    //         ConstraintViolationException ex,  WebRequest webRequest) {
    //         List<Map<String,String>> errors = ex.getConstraintViolations().stream()
    //             .map(constraintViolation ->{
    //                 Map<String,String> errMap = new HashMap<>();
    //                 errMap.put(constraintViolation.getPropertyPath().toString(),
    //                         constraintViolation.getMessage());
    //                 return errMap;
    //             }).toList();

    //     ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
    //         webRequest.getDescription(false),
    //         HttpStatus.BAD_REQUEST,
    //         errors.toString(),
    //         LocalDateTime.now()
    //     );
    //     return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    // }

    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

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

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
                                                                            
    }
}
