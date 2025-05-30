package com.justxtar.template.infra;

import java.util.Map;

public class ErrorResponse {
    public final String status;
    public final String message;
    public final String code;
    public Map<String, Object> data;

    public ErrorResponse(String status, String message, String code, Map<String, Object> data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.data = data;
    }
}
