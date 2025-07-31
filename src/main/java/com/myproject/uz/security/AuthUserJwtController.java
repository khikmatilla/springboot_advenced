package com.myproject.uz.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthUserJwtController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthUsersService authUsersService;


    public AuthUserJwtController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, AuthUsersService authUsersService) {
        this.authenticationManager = authenticationManager;

        this.jwtTokenProvider = jwtTokenProvider;
        this.authUsersService = authUsersService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVm loginVm) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginVm.getUserName(),
                        loginVm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication, loginVm.getRememberMe());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthUser> registerUser(@RequestBody AuthUser user) {

        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity("Password length must be between 4 and 16 characters", HttpStatus.CONFLICT);
        }
        if (authUsersService.checkUsername(user.getUserName())) {
            return new ResponseEntity("Username is already in use", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(authUsersService.save(user));
    }

    public Boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class JWTToken {
        private String token;
    }
}