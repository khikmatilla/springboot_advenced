package com.myproject.springboot_advenced.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.myproject.springboot_advenced.dto.Method;
import com.myproject.springboot_advenced.dto.Response;

public interface PaymeService {

    Response execute(JsonNode data);

    Method getPaymeMethod();
}
