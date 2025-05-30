package com.justxtar.template.user.command.repo;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.justxtar.template.user.command.model.UserModel;

@Repository
public interface UserRepo {
    void create(UserModel userModel);
    void update(UserModel userModel);
    void delete(Long id);
    Optional<UserModel> getById(Long id);
} 