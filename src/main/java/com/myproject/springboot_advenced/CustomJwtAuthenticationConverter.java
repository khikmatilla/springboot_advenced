//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.myproject.springboot_advenced;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

public class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    public CustomJwtAuthenticationConverter() {
    }

    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = this.defaultConverter.convert(jwt);
        List<String> realmRoles = jwt.getClaimAsMap("realm_access") != null ? (List)jwt.getClaimAsMap("realm_access").get("roles") : List.of();
        List<GrantedAuthority> keycloakAuthorities = realmRoles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        Collection<GrantedAuthority> safeAuthorities = authorities;
        return Stream.concat(safeAuthorities.stream(), keycloakAuthorities.stream()).collect(Collectors.toSet());
    }
}
