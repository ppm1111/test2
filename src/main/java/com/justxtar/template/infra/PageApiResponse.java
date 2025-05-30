package com.justxtar.template.infra;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class PageApiResponse<T> {
    public final String status;
    public final String message;
    public T data;
    public int totalCount;
    public int totalPage;
    public int pageNum;
    public int pageCount;

    public PageApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}