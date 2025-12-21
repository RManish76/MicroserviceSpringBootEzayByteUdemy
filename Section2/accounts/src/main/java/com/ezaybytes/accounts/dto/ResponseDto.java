package com.ezaybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
    name = "Response",
    description = "Schema to hold successful response information"
)
@Data @AllArgsConstructor
public class ResponseDto {

    //this class will be used to return statuscode and statusmsg in response of api calls.

    @Schema(
        description = "Status code in the response", example = "200"
    )
    private String statusCode;

    @Schema(
        description = "Status message in the response", example = "Request processed successfully"
    )
    private String statusMsg;
}
