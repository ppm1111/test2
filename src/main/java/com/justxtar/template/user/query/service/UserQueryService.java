package com.justxtar.template.user.query.service;

import java.util.List;

import com.justxtar.template.user.query.readmodel.UserReadModel;
import com.justxtar.template.user.query.service.dto.QueryUserDto;

public interface UserQueryService {
    UserReadModel queryById(Long id);
    List<UserReadModel> queryPage(int page, int size, QueryUserDto dto);
    int queryPageCount(QueryUserDto dto);
} 