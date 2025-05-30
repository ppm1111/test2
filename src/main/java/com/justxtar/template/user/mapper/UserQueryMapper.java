package com.justxtar.template.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.justxtar.template.user.query.readmodel.UserReadModel;

@Mapper
public interface UserQueryMapper {
    UserReadModel queryById(Long id);
    List<UserReadModel> queryPage(int offset, int limit);
    int queryPageCount();
} 