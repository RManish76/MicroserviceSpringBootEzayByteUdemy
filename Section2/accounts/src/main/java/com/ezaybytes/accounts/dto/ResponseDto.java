package com.ezaybytes.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ResponseDto {

    //this class will be used to return statuscode and statusmsg in response of api calls.

    private String statusCode;
    private String statusMsg;
}
