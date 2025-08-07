package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Request {
    private Long id;
    private Method method;
    private Params params;
}
