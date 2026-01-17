package com.eazybytes.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    
    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport(){ //since the gatewayserver is running of reactived spring, so we have to wrap the return type in Mono
        return Mono.just("An error occurred. Please try after some time or contact support team!!!"); //statment which we want to return.
    }
}
