package com.expenshare.model.mapper;

import com.expenshare.model.dto.user.AddressDto;
import org.mapstruct.Mapper;
import com.expenshare.model.dto.user.CreateUserRequest;
import com.expenshare.model.dto.user.UserDto;
import com.expenshare.model.entity.UserEntity;

@Mapper(componentModel = "jsr330")
public interface UserMapper {
    UserEntity.AddressEmbeddable toEntity(AddressDto dto);
    AddressDto toDto(UserEntity.AddressEmbeddable entity);
    UserEntity toEntity(CreateUserRequest req);
    UserDto toDto(UserEntity entity);
}
