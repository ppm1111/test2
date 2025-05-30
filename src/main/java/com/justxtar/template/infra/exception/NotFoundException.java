package com.justxtar.template.infra.exception;

import java.util.Map;

public class NotFoundException  extends BaseException {
    protected NotFoundException(String message, Map<String, Object> data) {
        super("NOT_FOUND", message, data);
    }
}
