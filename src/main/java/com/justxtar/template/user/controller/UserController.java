package com.justxtar.template.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justxtar.template.infra.ApiResponse;
import com.justxtar.template.infra.Constant;
import com.justxtar.template.infra.PageApiResponse;
import com.justxtar.template.infra.exception.NotFoundException;
import com.justxtar.template.infra.exception.http.NotFoundHttpException;
import com.justxtar.template.user.command.service.UserCommand;
import com.justxtar.template.user.command.service.dto.CreateUserDto;
import com.justxtar.template.user.command.service.dto.UpdateUserDto;
import com.justxtar.template.user.query.readmodel.UserReadModel;
import com.justxtar.template.user.query.service.UserQueryService;
import com.justxtar.template.user.query.service.dto.QueryUserDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserCommand userCommand;
    private final UserQueryService userQueryService;

    public UserController(UserCommand userCommandService, UserQueryService userQueryService) {
        this.userCommand = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid CreateUserDto dto) {
        userCommand.create(dto);
        return new ApiResponse<>(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody @Valid UpdateUserDto dto) {
        try {
            userCommand.update(id, dto);
            return new ApiResponse<>(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, null);
        } catch (NotFoundException e) {
            throw new NotFoundHttpException(e.getErrorCode(), e.getMessage(), e.getData());
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        try {
            userCommand.delete(id);
            return new ApiResponse<>(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, null);
        } catch (NotFoundException e) {
            throw new NotFoundHttpException(e.getErrorCode(), e.getMessage(), e.getData());
        }
    }

    @GetMapping
    public PageApiResponse<List<UserReadModel>> getPage(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
        ) {
        List<UserReadModel> userArr = userQueryService.queryPage(page, size, new QueryUserDto());
        int count = userQueryService.queryPageCount(new QueryUserDto());
        
        PageApiResponse<List<UserReadModel>> response = new PageApiResponse<List<UserReadModel>>(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, userArr);
        response.setTotalCount(count);
        response.setPageNum(page);
        response.setPageCount(size);
        response.setTotalPage((int) Math.ceil((double) count / size));
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserReadModel> getById(@PathVariable Long id) {
        try {
            UserReadModel user = userQueryService.queryById(id);
            return new ApiResponse<>(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, user);
        }  catch (NotFoundException e) {
            throw new NotFoundHttpException(e.getErrorCode(), e.getMessage(), e.getData());
        }
    }
} 