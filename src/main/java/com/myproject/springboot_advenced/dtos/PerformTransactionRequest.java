package com.myproject.springboot_advenced.dtos;

import java.util.Map;

public class PerformTransactionRequest {
    private String method;
    private Map<String, Object> params;
    private int id;

    // Getters and Setters

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
