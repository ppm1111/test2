package com.justxtar.template.infra.exception.http;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class NotFoundHttpException extends HttpException {
    public NotFoundHttpException(String errorCode, String message, Map<String, Object> data) {
        super(HttpStatus.NOT_FOUND, errorCode, message, data);
    }
}
