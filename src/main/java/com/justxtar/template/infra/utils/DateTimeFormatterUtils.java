package com.justxtar.template.infra.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日期時間格式化工具類
 * 專注於日期時間的解析與格式化
 */
@Component
public class DateTimeFormatterUtils {

    // 常用的格式化器
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_ONLY_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 將 LocalDateTime 格式化為預設格式字串
     * @param dateTime 日期時間
     * @return 格式化後的字串
     */
    public String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_FORMATTER);
    }

    /**
     * 將 LocalDateTime 格式化為指定格式字串
     * @param dateTime 日期時間
     * @param formatter 格式化器
     * @return 格式化後的字串
     */
    public String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(formatter);
    }

    /**
     * 將字串解析為 LocalDateTime（使用預設格式）
     * @param dateTimeString 日期時間字串
     * @return LocalDateTime
     * @throws DateTimeParseException 解析失敗時拋出
     */
    public LocalDateTime parse(String dateTimeString) {
        return parse(dateTimeString, DEFAULT_FORMATTER);
    }

    /**
     * 將字串解析為 LocalDateTime（使用指定格式）
     * @param dateTimeString 日期時間字串
     * @param formatter 格式化器
     * @return LocalDateTime
     * @throws DateTimeParseException 解析失敗時拋出
     */
    public LocalDateTime parse(String dateTimeString, DateTimeFormatter formatter) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeString.trim(), formatter);
    }

    /**
     * 嘗試用多種格式解析日期字串
     * @param dateTimeString 日期時間字串
     * @return LocalDateTime，解析失敗返回 null
     */
    public LocalDateTime parseFlexibly(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        // 清理字串
        String cleaned = cleanDateTimeString(dateTimeString);

        // 嘗試多種格式
        DateTimeFormatter[] formatters = {
                DEFAULT_FORMATTER,
                ISO_FORMATTER,
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(cleaned, formatter);
            } catch (DateTimeParseException ignored) {
                // 繼續嘗試下一個格式
            }
        }

        return null; // 所有格式都失敗
    }

    /**
     * 清理日期時間字串
     * @param dateTimeString 原始字串
     * @return 清理後的字串
     */
    private String cleanDateTimeString(String dateTimeString) {
        return dateTimeString
                .replace("\"", "")      // 移除引號
                .replace("T", " ")      // ISO 格式轉換
                .trim();
    }
}