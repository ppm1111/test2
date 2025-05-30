package com.justxtar.template.user.query.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.justxtar.template.user.exception.UserNotFoundException;
import com.justxtar.template.user.mapper.UserQueryMapper;
import com.justxtar.template.user.query.readmodel.UserReadModel;
import com.justxtar.template.user.query.service.UserQueryService;
import com.justxtar.template.user.query.service.dto.QueryUserDto;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserQueryMapper userMapper;

    public UserQueryServiceImpl(UserQueryMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserReadModel queryById(Long id) {
        UserReadModel user = userMapper.queryById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found", Map.of("id", id));
        }
        return user;
    }

    @Override
    public List<UserReadModel> queryPage(int page, int size, QueryUserDto dto) {
        int offset = (page - 1) * size;
        return userMapper.queryPage(offset, size);
    }

    @Override
    public int queryPageCount(QueryUserDto dto) {
        return userMapper.queryPageCount();
    }
} 