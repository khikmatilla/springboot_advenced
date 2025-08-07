package com.myproject.springboot_advenced.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T>{
    private T result;

    public <V, K> Result(Map<K, V> allow, Map<K, V> kvMap) {
        
    }
}
