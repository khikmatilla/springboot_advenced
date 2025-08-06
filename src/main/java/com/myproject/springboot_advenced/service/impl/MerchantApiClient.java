package com.myproject.springboot_advenced.service.impl;

import com.myproject.springboot_advenced.dtos.Account;
import com.myproject.springboot_advenced.dtos.Params;
import com.myproject.springboot_advenced.dtos.PerformTransactionRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.Map;

@Service
public class MerchantApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String performTransaction() {
        String url = "https://merchant/pay/";

        // Basic Auth: "Login:Pass" → base64
        String login = "Login";
        String password = "Pass";
        String credentials = Base64.getEncoder().encodeToString((login + ":" + password).getBytes());

        // Account
        Account account = new Account();
        account.setPhone("903595731");

        // Params
        Params params = new Params();
        params.setId("53327b3fc92af52c0b72b695");
        params.setTime(System.currentTimeMillis());
        params.setAmount(500000);
        params.setAccount(account);

        // Build final JSON body
        PerformTransactionRequest requestBody = new PerformTransactionRequest();
        requestBody.setMethod("PerformTransaction");
        requestBody.setParams(Map.of(
                "id", params.getId(),
                "time", params.getTime(),
                "amount", params.getAmount(),
                "account", Map.of("phone", account.getPhone())
        ));
        requestBody.setId(2032);

        // HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + credentials);

        HttpEntity<PerformTransactionRequest> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );

        return response.getBody();
    }
}
