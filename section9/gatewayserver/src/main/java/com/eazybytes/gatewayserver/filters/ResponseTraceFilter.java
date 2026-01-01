package com.eazybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import reactor.core.publisher.Mono;

//responsible to add the corelation/trace id to the response

@Configuration
public class ResponseTraceFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Bean
    public GlobalFilter posGlobalFilter(){
        return (exchange, chain) ->{ //accepting the server webexchange and chain as parameter
            return chain.filter(exchange).then(Mono.fromRunnable(()->{ //will trigger only when the request is sent to responsbile MS and recived the response form id as we are using chain and then method
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                String correlationId = filterUtility.getCorrelationId(requestHeaders);
                logger.debug("Updated the correlation id to the outbound headers: {}", correlationId);
                exchange.getResponse().getHeaders().add(FilterUtility.CORRELATION_ID, correlationId);
            }));
        };
    }
}
