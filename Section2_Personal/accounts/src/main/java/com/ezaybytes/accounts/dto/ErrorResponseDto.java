package com.ezaybytes.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {
    
    //this class will be responsible for sending error msg for rest api calls

    @Schema(
        description = "API path invoked by client"
    )
    private String apiPath; //we want send the api path as well with error msg in response

    @Schema(
        description = "Error Code representing the error hapeend"
    )
    private HttpStatus errorCode;

    @Schema(
        description = "Error message representing he error hapeened"
    )
    private String errorMessage;

    @Schema(
        description = "Time representing when the error happend"
    )
    private LocalDateTime errorTime;
}
