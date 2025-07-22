package com.myproject.springboot_advenced.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private long tokenValidityMillisecondsRemember;
    private long tokenValidateMilliSeconds;
    private final Key key;
    private final JwtParser jwtParser;

    public JwtTokenProvider() {
        byte[] keyBytes;
        String secret = "YXZlcnktc2VjdXJlLXNlY3JldC1rZXktd2l0aC1iYXNlNjQxMjM=";
        keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityMillisecondsRemember = 1000 * 86400;
        this.tokenValidateMilliSeconds = 1000 * 3600;
    }


    public String generateToken(Authentication authentication, Boolean rememberMe) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        long now = System.currentTimeMillis();
        Date validate;
        if (rememberMe) {
            validate = new Date(now + tokenValidityMillisecondsRemember);
        } else {
            validate = new Date(now + tokenValidateMilliSeconds);
        }
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validate)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token");
        } catch (SignatureException e) {
            log.error("Invalid JWT token");
        }
        return false;
    }


    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth").toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
