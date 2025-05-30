package com.justxtar.template.user.command.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.justxtar.template.user.command.model.UserModel;
import com.justxtar.template.user.command.repo.UserRepo;
import com.justxtar.template.user.command.service.UserCommand;
import com.justxtar.template.user.command.service.dto.CreateUserDto;
import com.justxtar.template.user.command.service.dto.UpdateUserDto;
import com.justxtar.template.user.exception.UserNotFoundException;

@Service
public class UserCommandImpl implements UserCommand {
    private final UserRepo userRepo;

    public UserCommandImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void create(CreateUserDto dto) {
        UserModel userModel = new UserModel();
        userModel.setName(dto.getName());
        userModel.setEmail(dto.getEmail());
        userRepo.create(userModel);
    }

    @Override
    public void update(Long id, UpdateUserDto dto) {
        Optional<UserModel> user = userRepo.getById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found", null);
        }
        UserModel userModel = user.get();
        userModel.setName(dto.getName());
        userModel.setEmail(dto.getEmail());
        userRepo.update(userModel);
    }

    @Override
    public void delete(Long id) {
        Optional<UserModel> userOpt = userRepo.getById(id);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found or already deleted", null);
        }
        userRepo.delete(id);
    }
} 