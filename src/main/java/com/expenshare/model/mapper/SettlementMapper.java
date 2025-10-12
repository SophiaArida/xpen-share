package com.expenshare.model.mapper;

import com.expenshare.model.dto.settlement.SettlementDto;
import com.expenshare.model.entity.SettlementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jsr330")
public interface SettlementMapper {

//    SettlementEntity toEntity(CreateSettlementRequest req);
    @Mapping(target = "groupId", expression = "java(entity.getGroupId().getGroupId())")
    @Mapping(target = "fromUserId", expression = "java(entity.getFromUserId().getUserId())")
    @Mapping(target = "toUserId", expression = "java(entity.getToUserId().getUserId())")

    SettlementDto toDto (SettlementEntity entity);
}
