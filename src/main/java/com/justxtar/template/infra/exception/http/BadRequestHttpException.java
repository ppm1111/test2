package com.justxtar.template.infra.exception.http;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class BadRequestHttpException extends HttpException {
    public BadRequestHttpException(String errorCode, String message, Map<String, Object> data) {
        super(HttpStatus.BAD_REQUEST, errorCode, message, data);
    }
}
