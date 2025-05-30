package com.justxtar.template.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.justxtar.template.user.command.model.UserModel;

@Mapper
public interface UserCommandMapper {
    void create(UserModel user);
    void update(UserModel user);
    void delete(Long id);
    UserModel getById(Long id);
} 