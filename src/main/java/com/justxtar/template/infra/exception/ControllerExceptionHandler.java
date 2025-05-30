package com.justxtar.template.infra.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.justxtar.template.infra.ErrorResponse;
import com.justxtar.template.infra.Logger;
import com.justxtar.template.infra.exception.http.BadRequestHttpException;
import com.justxtar.template.infra.exception.http.InternalServerErrorHttpException;
import com.justxtar.template.infra.exception.http.NotFoundHttpException;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @Value("${app.debug:false}")
    private boolean debug;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        Map<String, Object> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errors.put(StrUtil.toUnderlineCase(error.getField()), error.getDefaultMessage())
        );
        result.getGlobalErrors().forEach(error -> {
            Object[] arguments = error.getArguments();
            if (arguments != null && arguments.length >= 2) {
                errors.put(StrUtil.toUnderlineCase(arguments[1].toString()), error.getDefaultMessage());
            }
        });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                new ErrorResponse("error", "parameter_error", "PARAMETER_ERROR",  errors)
            );
    }

    @ExceptionHandler(NotFoundHttpException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundHttpException ex,
                                                           WebRequest request) {
        String errorObj = ex.getMessage();
        String errorCode = ex.getErrorCode().toUpperCase();

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                new ErrorResponse("error", ex.getMessage(), errorCode, ex.getData())
            );
    }

    @ExceptionHandler(BadRequestHttpException.class)
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestHttpException ex,
                                                             WebRequest request) {
        String errorObj = ex.getMessage();
        String errorCode = ex.getErrorCode().toUpperCase();

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                new ErrorResponse("error", ex.getMessage(), errorCode, ex.getData())
            );
    }

    @ExceptionHandler(InternalServerErrorHttpException.class)
    public ResponseEntity<ErrorResponse> internalServerException(
            InternalServerErrorHttpException ex,
           WebRequest request
    ) {
        String errorObj = ex.getMessage();
        String errorCode = ex.getErrorCode().toUpperCase();

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ErrorResponse("error", ex.getMessage(), errorCode, ex.getData())
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> baseException(
            Exception ex,
            HttpServletRequest request
    ) {
        try {
            Map<String, Object> data = new HashMap<>();
            String errorMessage = ex.getMessage();
            String errorType = ex.getClass().getSimpleName();
            String stackTrace = getStackTraceInfo(ex);

            // 如果沒有 message，使用更有意義的資訊
            if (errorMessage == null || errorMessage.isBlank()) {
                if (ex instanceof NullPointerException) {
                    errorMessage = String.format("NullPointerException at %s",
                            getErrorLocation(ex));
                } else {
                    errorMessage = String.format("%s: %s", errorType,
                            getErrorLocation(ex));
                }
            }

            // 記錄完整錯誤
            Logger.error("API Error: " + errorMessage, ex);

            // 開發環境可以加入更多除錯資訊
            if (debug) {
                Map<String, Object> debugInfo = new HashMap<>();
                debugInfo.put("exception", errorType);
                debugInfo.put("path", request.getRequestURI());
                debugInfo.put("method", request.getMethod());
                debugInfo.put("stack_trace", stackTrace);
                data.put("debug_info", debugInfo);
            }

            ex.printStackTrace();
            Logger.error(errorMessage, ex);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ErrorResponse("error", errorMessage, "SYSTEM_ERROR", data)
                    );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ErrorResponse("error", e.getMessage(), "SYSTEM_ERROR", null)
                    );
        }
    }

    // 獲取錯誤位置
    private String getErrorLocation(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace.length > 0) {
            StackTraceElement element = stackTrace[0];
            return String.format("%s.%s(%s:%d)",
                    element.getClassName(),
                    element.getMethodName(),
                    element.getFileName(),
                    element.getLineNumber()
            );
        }
        return "Unknown location";
    }

    // 獲取堆疊追蹤資訊（前幾行）
    private String getStackTraceInfo(Exception ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        StringBuilder sb = new StringBuilder();

        // 只取前 5 行相關的堆疊追蹤
        for (int i = 0; i < Math.min(5, stackTrace.length); i++) {
            StackTraceElement element = stackTrace[i];
            // 只包含您的套件名稱
            if (element.getClassName().contains("com.tsc")) {
                sb.append(element.toString()).append("\n");
            }
        }

        return sb.toString();
    }
}
