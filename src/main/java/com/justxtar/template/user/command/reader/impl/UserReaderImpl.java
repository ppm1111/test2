package com.justxtar.template.user.command.reader.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.justxtar.template.user.command.model.UserModel;
import com.justxtar.template.user.command.reader.UserReader;
import com.justxtar.template.user.mapper.UserCommandMapper;

@Component
public class UserReaderImpl implements UserReader {
    private final UserCommandMapper userMapper;

    public UserReaderImpl(UserCommandMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserModel> getById(Long id) {
        return Optional.ofNullable(userMapper.getById(id));
    }
} 