package com.justxtar.template.infra;

public class ApiResponse<T> {
    public final String status;
    public final String message;
    public T data;

    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
