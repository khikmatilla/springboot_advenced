package com.myproject.springboot_advenced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private Boolean success;
    private String message;
    private T data;

    public BaseResponse(T data) {
        this.success = true;
        this.message = "SUCCESS";
        this.data = data;
    }

    public BaseResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, message, null);
    }

    public static <T> BaseResponse<T> deleted() {
        return new BaseResponse<>(true, "deleted", null);
    }
}
