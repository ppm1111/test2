package com.justxtar.template.infra.exception;

import java.util.Map;

public abstract class BusinessException extends BaseException {
    protected BusinessException(String message, Map<String, Object> data) {
        super("BUSINESS_ERROR", message, data);
    }
}
