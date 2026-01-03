package com.eazybytes.gatewayserver.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

//responsible to generate the trace/CoRelatiion id

@Order(1) //Sometimes we want to define mutliple filter then also want to execute in order. This annotation
            //helps in the order of execution of the filters like 1,2,3...etc.
            //Since we have given 1 in order which will make sure always this filter will be executed first inside the gatewayserver
@Component // To make bean of this class
public class RequestTraceFilter implements GlobalFilter {

    //Whenever we want out filter to be executed for all kind of traffic that goingi t be received by our
    //gateway server then we need to make sure we are implementing this interface which GlobalFilter.

    
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    //Please note that the project of Spring cloud Gateway Server is built based upon the spring reactive
    //module. That's why we are able to see some different code like ServerWebExchange Mono<Void>. there are all related react to module
    //Mono --> Indicates a single object return type and Mono<Void> says returning nothing
    //Flux --> Indicates collection object return type
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders(); //fetching header
        //handles case if due to some reason same an already reviced request again recived at gatewayserver.
        // and in that case we don't want to overwrite the correlation id.
        if(isCorrelationIdPresent(requestHeaders)){ //checking if correlationId is already there r not
            logger.debug("eazyBank-correlation-id found in RequestRraceFilter:  {}",
                filterUtility.getCorrelationId(requestHeaders)
            );
        } else{
            String correlationID = generateCorrelationId(); //generates random id as corelation id
            exchange = filterUtility.setCorreltaionId(exchange, correlationID);
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter: {}",correlationID);
        }
        return chain.filter(exchange);
    }
    


    //Checking if correlationId is already there
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders){
        if(filterUtility.getCorrelationId(requestHeaders)!=null){ 
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}
