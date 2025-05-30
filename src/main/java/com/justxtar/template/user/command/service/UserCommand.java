package com.justxtar.template.user.command.service;

import com.justxtar.template.user.command.service.dto.CreateUserDto;
import com.justxtar.template.user.command.service.dto.UpdateUserDto;

public interface UserCommand {
    void create(CreateUserDto dto);
    void update(Long id, UpdateUserDto dto);
    void delete(Long id);
} 