package com.expenshare.service;

import com.expenshare.event.EventTopics;
import com.expenshare.event.KafkaProducer;
import com.expenshare.model.dto.settlement.CreateSettlementRequest;
import com.expenshare.model.dto.settlement.SettlementDto;
import com.expenshare.model.entity.GroupEntity;
import com.expenshare.model.entity.SettlementEntity;
import com.expenshare.model.entity.UserEntity;
import com.expenshare.model.mapper.SettlementMapper;
import com.expenshare.repository.facade.SettlementRepositoryFacade;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class SettlementService {
    private final SettlementRepositoryFacade facade;
    private final SettlementMapper mapper;
    private final UserService userService;
    private final GroupService groupService;
    private final KafkaProducer kafkaProducer;

    public SettlementService(SettlementRepositoryFacade facade, SettlementMapper mapper,
                             UserService userService, GroupService groupService,
                             KafkaProducer kafkaProducer) {
        this.facade = facade;
        this.mapper = mapper;
        this.userService = userService;
        this.groupService = groupService;
        this.kafkaProducer = kafkaProducer;

    }

    public SettlementDto createSettlement(CreateSettlementRequest request) {
        SettlementEntity entity = new SettlementEntity();
        GroupEntity group = groupService.getGroupEntity(request.getGroupId());
        entity.setGroup(group);
        UserEntity fromUser = userService.getUserEntity(request.getFromUserId());
        entity.setFromUser(fromUser);
        UserEntity toUser = userService.getUserEntity(request.getToUserId());
        entity.setToUser(toUser);
        entity.setAmount(request.getAmount());
        entity.setMethod(request.getMethod());
        entity.setNote(request.getNote());
        entity.setReference(request.getReference());

        SettlementEntity savedEntity = facade.createConfirmed(entity);
        // publish settlement.confirmed event
        Map<String, Object> settlementPayload = Map.of(
                "settlementId", savedEntity.getSettlementId(),
                "groupId", savedEntity.getGroup().getGroupId(),
                "fromUserId", savedEntity.getFromUser().getUserId(),
                "toUserId", savedEntity.getToUser().getUserId(),
                "amount", savedEntity.getAmount(),
                "method", savedEntity.getMethod(),
                "note", savedEntity.getNote()
        );

        kafkaProducer.publish(
                EventTopics.SETTLEMENT_CONFIRMED,
                String.valueOf(savedEntity.getGroup().getGroupId()), // key = groupId
                settlementPayload
        );
        return mapper.toDto(savedEntity);
    }
}
