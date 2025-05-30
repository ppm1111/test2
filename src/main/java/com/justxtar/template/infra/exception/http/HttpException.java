package com.justxtar.template.infra.exception.http;

import org.springframework.http.HttpStatus;

import java.util.Map;

public abstract class HttpException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;
    private Map<String, Object> data;

    protected HttpException(HttpStatus status, String errorCode, String message, Map<String, Object> data) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.data = data;
    }

    protected HttpException(HttpStatus status, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() { return status; }
    public String getErrorCode() { return errorCode; }
    public Map<String, Object> getData() { return data; }
}
