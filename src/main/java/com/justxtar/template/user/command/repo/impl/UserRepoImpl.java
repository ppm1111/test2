package com.justxtar.template.user.command.repo.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.justxtar.template.user.command.model.UserModel;
import com.justxtar.template.user.command.repo.UserRepo;
import com.justxtar.template.user.mapper.UserCommandMapper;

@Repository
public class UserRepoImpl implements UserRepo {
    private final UserCommandMapper userMapper;

    public UserRepoImpl(UserCommandMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void create(UserModel userModel) {
        userMapper.create(userModel);
    }

    @Override
    public void update(UserModel userModel) {
        userMapper.update(userModel);
    }

    @Override
    public void delete(Long id) {
        userMapper.delete(id);
    }

    @Override
    public Optional<UserModel> getById(Long id) {
        return Optional.ofNullable(userMapper.getById(id));
    }
} 