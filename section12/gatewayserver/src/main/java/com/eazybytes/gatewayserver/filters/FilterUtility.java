package com.eazybytes.gatewayserver.filters;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

//this class will store the common logic between RequestTraceFilter and ResponseTraceFilter class.

@Component
public class FilterUtility {
    
    public static final String CORRELATION_ID = "eazybank-correlation-id"; //header name whose  is correlationId

    //checking if "eazybank-correlation-id" header which is titile/name of header where our correlation id is stored.
    //if present return the value of the header else return null
    public String getCorrelationId(HttpHeaders requestHeaders){
        if(requestHeaders.get(CORRELATION_ID)!=null){
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            return requestHeaderList.stream().findFirst().get();
        } else {
            return null;
        }
    }

    private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name,
            String value) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
    }

    public ServerWebExchange setCorreltaionId(ServerWebExchange exchange, String correlationID) {
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationID); //setting the header name and value which we recived in the request.
    }


}
