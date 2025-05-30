package com.justxtar.template.infra;

import org.slf4j.LoggerFactory;

public class Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
    private static final org.slf4j.Logger rootLogger = LoggerFactory.getLogger("");

    public static void error(String message, Exception e) {
        logger.error(message, e);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void trace(String message) {
        logger.trace(message);
    }

    private static String getCaller() {
        // 獲取調用堆棧
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 預設值
        String level1 = "Unknown(Unknown:0)";
        String level2 = "Unknown(Unknown:0)";

        // 檢查堆棧深度並獲取兩層
        if (stackTrace.length >= 4) {
            // 第一層：stackTrace[3]（實際調用 LoggerService 的地方）
            StackTraceElement caller1 = stackTrace[3];
            level1 = caller1.getClassName() + "." + caller1.getMethodName() + "(" + caller1.getFileName() + ":" + caller1.getLineNumber() + ")";
        }
        if (stackTrace.length >= 5) {
            // 第二層：stackTrace[4]（上一層的調用者）
            StackTraceElement caller2 = stackTrace[4];
            level2 = caller2.getClassName() + "." + caller2.getMethodName() + "(" + caller2.getFileName() + ":" + caller2.getLineNumber() + ")";
        }

        // 合併成一個字串
        return level1 + " <- " + level2;
    }
}
