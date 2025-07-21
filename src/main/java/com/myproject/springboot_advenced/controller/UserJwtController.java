package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.domain.Users;
import com.myproject.springboot_advenced.repository.UsersRepository;
import com.myproject.springboot_advenced.security.JwtTokenProvider;
import com.myproject.springboot_advenced.service.UsersService;
import com.myproject.springboot_advenced.veb.rest.vm.LoginVm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserJwtController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;

    private final UsersRepository usersRepository;

    public UserJwtController(AuthenticationManager authenticationManager, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, UsersRepository usersRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody LoginVm loginVm) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVm.getUserName(), loginVm.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication, loginVm.getRememberMe())

        return ResponseEntity.ok(map);
    }
}
