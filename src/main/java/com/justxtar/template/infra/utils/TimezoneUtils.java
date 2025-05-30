package com.justxtar.template.infra.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 時區轉換工具類
 * 專注於顯示時區與儲存時區之間的轉換
 */
@Component
public class TimezoneUtils {
    private final ZoneId displayZone;
    private final ZoneId storageZone;

    public TimezoneUtils(
            @Value("${app.timezone.display:Asia/Taipei}") String displayTimezone,
            @Value("${app.timezone.storage:UTC}") String storageTimezone) {
        this.displayZone = ZoneId.of(displayTimezone);
        this.storageZone = ZoneId.of(storageTimezone);
    }

    /**
     * 將顯示時區的時間轉換為儲存時區的時間
     * @param displayDateTime 顯示時區的時間
     * @return 儲存時區的時間
     */
    public LocalDateTime toStorageTime(LocalDateTime displayDateTime) {
        if (displayDateTime == null) {
            return null;
        }
        return displayDateTime
                .atZone(displayZone)
                .withZoneSameInstant(storageZone)
                .toLocalDateTime();
    }

    /**
     * 將儲存時區的時間轉換為顯示時區的時間
     * @param storageDateTime 儲存時區的時間
     * @return 顯示時區的時間
     */
    public LocalDateTime toDisplayTime(LocalDateTime storageDateTime) {
        if (storageDateTime == null) {
            return null;
        }
        return storageDateTime
                .atZone(storageZone)
                .withZoneSameInstant(displayZone)
                .toLocalDateTime();
    }

    /**
     * 獲取當前的顯示時區時間
     * @return 當前顯示時區時間
     */
    public LocalDateTime nowInDisplayZone() {
        return ZonedDateTime.now(displayZone).toLocalDateTime();
    }

    /**
     * 獲取當前的儲存時區時間
     * @return 當前儲存時區時間
     */
    public LocalDateTime nowInStorageZone() {
        return ZonedDateTime.now(storageZone).toLocalDateTime();
    }

    /**
     * 獲取顯示時區
     * @return 顯示時區
     */
    public ZoneId getDisplayZone() {
        return displayZone;
    }

    /**
     * 獲取儲存時區
     * @return 儲存時區
     */
    public ZoneId getStorageZone() {
        return storageZone;
    }
}