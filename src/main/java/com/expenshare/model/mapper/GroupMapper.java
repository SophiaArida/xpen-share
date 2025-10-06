package com.expenshare.model.mapper;

import com.expenshare.model.dto.group.GroupDto;
import com.expenshare.model.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "jsr330", imports = {Collectors.class}) // works well with Micronaut DI
public interface GroupMapper {

    GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt())")
    @Mapping(
            target = "members",
            expression = "java(entity.getMembers().stream()" +
                    ".map(m -> m.getUser().getUserId())" +
                    ".collect(Collectors.toList()))"
    )
    GroupDto toDTO(GroupEntity entity);
}
