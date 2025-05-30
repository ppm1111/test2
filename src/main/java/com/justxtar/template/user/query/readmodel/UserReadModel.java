package com.justxtar.template.user.query.readmodel;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserReadModel {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
} 