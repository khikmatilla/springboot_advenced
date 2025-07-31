package com.myproject.uz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginVm {
    private String userName;
    private String password;
    private Boolean rememberMe;

}