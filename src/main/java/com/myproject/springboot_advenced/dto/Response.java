package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private JsonNode result;
    private Error error;

    public Response(JsonNode result) {
        this.result = result;
        this.error = null;
    }

    public Response(Error error) {
        this.result = null;
        this.error = error;
    }
}
