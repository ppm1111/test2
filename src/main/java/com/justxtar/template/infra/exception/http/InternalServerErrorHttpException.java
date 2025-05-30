package com.justxtar.template.infra.exception.http;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class InternalServerErrorHttpException extends HttpException {
    public InternalServerErrorHttpException(String errorCode, String message, Map<String, Object> data) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorCode, message, data);
    }
}
