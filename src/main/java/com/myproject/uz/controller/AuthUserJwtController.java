package com.myproject.uz.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myproject.uz.dto.LoginVm;
import com.myproject.uz.entity.AuthUser;
import com.myproject.uz.security.JwtTokenProvider;
import com.myproject.uz.service.UsersService;

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

@RestController
@RequestMapping("/api/auth")
public class AuthUserJwtController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UsersService usersService;


    public AuthUserJwtController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsersService usersService) {
        this.authenticationManager = authenticationManager;

        this.jwtTokenProvider = jwtTokenProvider;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody LoginVm loginVm) {

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
        return new ResponseEntity<>(new JWTToken(jwt), headers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthUser> registerUser(@RequestBody AuthUser user) {

        if (!checkPasswordLength(user.getPassword())) {
            return new ResponseEntity("Password length must be between 4 and 16 characters", HttpStatus.CONFLICT);
        }
        if (usersService.checkUsername(user.getUserName())) {
            return new ResponseEntity("Username is already in use", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(usersService.save(user));
    }

    public Boolean checkPasswordLength(String password) {
        return password.length() >= 4;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class JWTToken {
        @JsonProperty("jwt-token")
        private String token;
    }
}