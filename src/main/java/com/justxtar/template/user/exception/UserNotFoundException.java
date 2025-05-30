package com.justxtar.template.user.exception;

import com.justxtar.template.infra.exception.BusinessException;

import java.util.Map;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message, Map<String, Object> data) {
        super(message, data);
    }
} 