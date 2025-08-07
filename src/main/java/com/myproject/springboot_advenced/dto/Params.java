package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Params {
    private Integer id;
    private Long time;
    private Long amount;
    Map<String, String> account;
}
