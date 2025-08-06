package com.myproject.springboot_advenced.controller;

import com.myproject.springboot_advenced.service.impl.MerchantApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {

    private final MerchantApiClient merchantApiClient;

    public MerchantController(MerchantApiClient merchantApiClient) {
        this.merchantApiClient = merchantApiClient;
    }

    @GetMapping("/test-payment")
    public String testPayment() {
        return merchantApiClient.performTransaction();
    }
}
