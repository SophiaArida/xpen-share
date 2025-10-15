package com.expenshare.service;

import com.expenshare.event.KafkaProducer;
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
    private final KafkaProducer kafkaProducer;

    public UserService(UserRepositoryFacade facade, UserMapper mapper, KafkaProducer kafkaProducer) {
        this.facade = facade;
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
    }

    public UserDto createUser(CreateUserRequest req) {
        if (facade.emailExists(req.getEmail())) {
            throw new ConflictException("Email already exists: " + req.getEmail());
        }
        UserEntity entity = mapper.toEntity(req);
        UserEntity saved = facade.create(entity);
        UserDto dto = mapper.toDto(saved);

        kafkaProducer.publishUserCreatedEvent("{\"userId\": " + saved.getUserId() + "}");
        return dto;
    }

    public UserDto getUser(Long id) {
        UserEntity entity = facade.getOrThrow(id);
        return mapper.toDto(entity);
    }

    public UserEntity getUserEntity(long id){
        return facade.getOrThrow(id);
    }
}
