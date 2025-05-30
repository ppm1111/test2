package com.justxtar.template.user.command.reader;

import java.util.Optional;

import com.justxtar.template.user.command.model.UserModel;

public interface UserReader {
    Optional<UserModel> getById(Long id);
} 