package com.justxtar.template.infra.exception;

import java.util.Map;

public abstract class BaseException extends RuntimeException {
    private final String errorCode;
    private Map<String, Object> data;

    protected BaseException(String errorCode, String message, Map<String, Object> data) {
        super(message);
        this.errorCode = errorCode;
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Map<String, Object> getData() {
        return data;
    }
}