package com.eazybytes.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
// import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder; //gemini suggestion of an error not needed
// import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder; 

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        // serverHttpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated()) // api to be authenticated,  .anyExchange() --> all api endpoint
        // serverHttpSecurity.authorizeExchange(exchanges -> exchanges.anyExchange().permitAll()) //permit all without authentication,    .anyExchange() --> all api endpoint

        //We want some api which does not require authentication where as for some we what authentication.

        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll() //permit without authentication for all GET request.
                                                    // .pathMatchers("/eazybank/accounts/**").authenticated() //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    // .pathMatchers("/eazybank/cards/**").authenticated()  //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    // .pathMatchers("/eazybank/loans/**").authenticated())  //required authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.

                                                        .pathMatchers("/eazybank/accounts/h2-console/**").permitAll() //so that without authentication we can login at h2-consol
                                                        .pathMatchers("/eazybank/loans/h2-console/**").permitAll() //so that without authentication we can login at h2-consol
                                                        .pathMatchers("/eazybank/cards/h2-console/**").permitAll() //so that without authentication we can login at h2-consol

                                                    .pathMatchers("/eazybank/accounts/**").hasRole("ACCOUNTS") //required authorication with role(ACCOUNTS) and authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .pathMatchers("/eazybank/cards/**").hasRole("CARDS")  //required authorication with role(ACCOUNTS) and authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .pathMatchers("/eazybank/loans/**").hasRole("LOANS"))  //required authorication with role(ACCOUNTS) and authentication if pattern matched. //As the GET is 1st, then GET'll have high priority means even if current pattern matced with GET one.
                                                    .oauth2ResourceServer(oAuth2ResourceServerSpec->oAuth2ResourceServerSpec
                                                                            // .jwt(Customizer.withDefaults())); //to use jwt and use default configuration
                                                                            //no need of default since we written custom jwt converter and check
                                                                            .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))); //establised the link between KeycloakRoleConverer and SecurityConfig.java

        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()); //disabling csrf protection which is enabled by default in spring security. Useful if browser/UI based application is there. //if not disabled all request of post(), put(), delete() will fail.
        
        serverHttpSecurity.headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.disable())); //by gemini to access the h2 console database through gatewayserver
        
        return serverHttpSecurity.build();

    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor(){
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter()); // telling where we written the logic for converter
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
     //gemini suggestion of an error not needed
    // @Bean
    // public ReactiveJwtDecoder jwtDecoder() {
    //     return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:7080/realms/master/protocol/openid-connect/certs").build();
    // }
}
