package com.eazybytes.gatewayserver.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        //we need to send detail of roles to spring security in fromat of GrantedAuthority for any role related security.

        Map<String, Object> realmAccess = (Map<String, Object>)source.getClaims().get("realm_access"); //getClaims will fetch the payload and from that we are fetching realm_access where all assigned roles are

        //checking if we got some roles
        if(realmAccess == null || realmAccess.isEmpty()){
            return new ArrayList<>();
        }
        
        Collection<GrantedAuthority> returnValue = ((List<String>)realmAccess.get("roles"))
                                        .stream().map(roleName -> "ROLE_" + roleName) //pulling all roles and adding ROLE_ as prefix.
                                                                            // Internally spring security whatever parameter passed in .hasRole adds ROLE_ prefix to the role which we have added in SecurityConfig
                                                                            // at rest pattern endpoint and checks with same, since we are passing the ROLE mannually we are adding ROLE_ prefix.
                                                                            // for example in SecurityConfig.java we have given role as "ACCOUNTS" spring will check "ROLE_ACCOUNTS"
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());
        
        return returnValue;                                        
    }
    
}
