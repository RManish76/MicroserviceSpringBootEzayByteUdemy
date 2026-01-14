package com.eazybytes.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder; //gemini suggestion of an error not needed
// import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder; 

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        // serverHttpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated()) // api to be authenticated,  .anyExchange() --> all api endpoint
        // serverHttpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll()) //permit all without authentication,    .anyExchange() --> all api endpoint

        //We want some api which does not require authentication where as for some we what authentication.

        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll() //permit without authentication for all GET request.
                                                    .pathMatchers("/eazybank/accounts/**").authenticated() //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .pathMatchers("/eazybank/cards/**").authenticated()  //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .pathMatchers("/eazybank/loans/**").authenticated())  //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .oauth2ResourceServer(oAuth2ResourceServerSpec->oAuth2ResourceServerSpec
                                                                            .jwt(Customizer.withDefaults())); //to use jwt and use default configuration

        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()); //disabling csrf protection which is enabled by default in spring security. Useful if browser/UI based application is there. //if not disabled all request of post(), put(), delete() will fail.
        
        return serverHttpSecurity.build();

    }

     //gemini suggestion of an error not needed
    // @Bean
    // public ReactiveJwtDecoder jwtDecoder() {
    //     return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:7080/realms/master/protocol/openid-connect/certs").build();
    // }
}
