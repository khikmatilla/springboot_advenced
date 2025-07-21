package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.domain.Users;
import com.myproject.springboot_advenced.repository.UsersRepository;
import com.myproject.springboot_advenced.security.JwtTokenProvider;
import com.myproject.springboot_advenced.service.UsersService;
import com.myproject.springboot_advenced.veb.rest.vm.LoginVm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UsersRepository usersRepository;

    public UserJwtController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersRepository = usersRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVm loginVm) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVm.getUserName(), loginVm.getPassword()));
        Users user = usersRepository.findByLogin(loginVm.getUserName());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + loginVm.getUserName());
        }
        String token = jwtTokenProvider.generateToken(loginVm.getUserName(), user.getRoles());
        Map<Object, Object> map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("token", token);
        return ResponseEntity.ok(map);
    }
}
