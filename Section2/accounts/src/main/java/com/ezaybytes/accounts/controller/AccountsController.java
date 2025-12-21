package com.ezaybytes.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezaybytes.accounts.constants.AccountsConstants;
import com.ezaybytes.accounts.dto.CustomerDto;
import com.ezaybytes.accounts.dto.ResponseDto;
import com.ezaybytes.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(
    name = "CRUD REST APIs for Accounts in EazyBank",
    description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
// path -> all api call will start with domain/api/...   and mediatype tells what format of data will be used which is json
@AllArgsConstructor
@Validated //tells spring that validation should be perfromed in this classs
public class AccountsController {

    //we have seen already in case of a single constructor in a class we don't need autowire
    //it'll automatically autowire as we have mentioned @AllArgsConstructor which will accecpt below param for below
    private IAccountsService iAccountsService;

    @Operation( //summary and detail for endpoint in api documentation
        summary = "Create Account REST API",
        description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponse( //example value of response statusCode and Status msg
        responseCode = "201",
        description = "HTPP Status CREARTED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        //@Valid tells that recived body is valid or not as per the DTOs and validAnnotation at DTO
        iAccountsService.createAccount(customerDto); //while creating account if exception occured it'll
        //go to global exception handler. it'll not come to our controller.
        //and from global exception handler will get there response.


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }


    @Operation(
        summary = "Fech Account Details REST API",
        description = "REST API to fetch Customer & Account details based on a mobile number"
    )
    @ApiResponse(
        responseCode = "200",
        description = "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam 
                                                @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    @Operation(
        summary = "Update Account Details REST API",
        description = "REST API to update Customer & Account details based on an account number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
        )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        //@Valid tells that recived body is valid or not as per the DTOs and validAnnotation at DTO
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


    @Operation(
        summary = "Delete Account & Customer Details REST API",
        description = "REST API to delete Customer & Account details based on a mobile number"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error"
        )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam 
                                                        @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")      
                                                        String mobileNumber){
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }
}
