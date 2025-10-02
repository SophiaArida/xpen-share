package com.expenshare.controller;

import com.expenshare.model.dto.user.CreateUserRequest;
import com.expenshare.model.dto.user.UserDto;
import com.expenshare.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

@Controller("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Post
    public HttpResponse<UserDto> createUser(@Body CreateUserRequest req) {
        UserDto dto = service.createUser(req);
        return HttpResponse.created(dto);
    }

    @Get("/{id}")
    public UserDto getUser(Long id) {
        return service.getUser(id);
    }
}
