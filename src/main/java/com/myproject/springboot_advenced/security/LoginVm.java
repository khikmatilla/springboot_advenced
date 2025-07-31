package com.myproject.springboot_advenced.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVm {
    private String userName;
    private String password;
    private Boolean rememberMe;

}