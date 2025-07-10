package com.myproject.springboot_advenced;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppErrorDto {

    private String errorPath;
    private int errorCode;
    private String friendlyMessage;
    private Object developerMessage;
}
