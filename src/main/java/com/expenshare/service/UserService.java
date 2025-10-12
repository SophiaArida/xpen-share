package com.expenshare.service;

import com.expenshare.exception.ConflictException;
import com.expenshare.repository.facade.UserRepositoryFacade;
import com.expenshare.model.dto.user.CreateUserRequest;
import com.expenshare.model.dto.user.UserDto;
import com.expenshare.model.entity.UserEntity;
import com.expenshare.model.mapper.UserMapper;
import jakarta.inject.Singleton;

@Singleton
public class UserService {

    private final UserRepositoryFacade facade;
    private final UserMapper mapper;

    public UserService(UserRepositoryFacade facade, UserMapper mapper) {
        this.facade = facade;
        this.mapper = mapper;
    }

    public UserDto createUser(CreateUserRequest req) {
        if (facade.emailExists(req.email)) {
            throw new ConflictException("Email already exists: " + req.email);
        }
        UserEntity entity = mapper.toEntity(req);
        UserEntity saved = facade.create(entity);
        return mapper.toDto(saved);
    }

    public UserDto getUser(Long id) {
        UserEntity entity = facade.getOrThrow(id);
        return mapper.toDto(entity);
    }

    public UserEntity getUserEntity(long id){
        return facade.getOrThrow(id);
    }
}
