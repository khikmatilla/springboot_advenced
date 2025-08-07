package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dto.Method;
import com.myproject.springboot_advenced.dto.Request;
import com.myproject.springboot_advenced.dto.Response;
import com.myproject.springboot_advenced.service.PaymeService;
import org.springframework.stereotype.Service;
import com.myproject.springboot_advenced.dto.Error;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymeResolverService {

    private final Map<Method, PaymeService> paymeServicesMap = new EnumMap<>(Method.class);

    public PaymeResolverService(List<PaymeService> paymeServices) {
        for (PaymeService paymeService : paymeServices) {
            paymeServicesMap.put(paymeService.getPaymeMethod(), paymeService);
        }
    }

    public Response resolve(Request request) {
        if (paymeServicesMap.containsKey(request.getMethod())) {
            return paymeServicesMap.get(request.getMethod()).execute(request.getParams());
        }
        return Response.builder()
                .error(Error.METHOD_NOT_FOUND_ERROR(null))
                .build();
    }
}
