package com.expenshare.service;

import com.expenshare.event.EventTopics;
import com.expenshare.event.KafkaProducer;
import com.expenshare.model.dto.expense.ShareDto;
import com.expenshare.model.dto.group.AddMembersRequest;
import com.expenshare.model.dto.group.GroupDto;
import com.expenshare.model.dto.group.CreateGroupRequest;
import com.expenshare.model.dto.settlement.RequestSuggestionDto;
import com.expenshare.model.dto.settlement.SettlementDto;
import com.expenshare.model.dto.settlement.SuggestionDto;
import com.expenshare.model.entity.ExpenseShareEntity;
import com.expenshare.model.entity.SettlementEntity;
import com.expenshare.model.mapper.GroupMapper;
import com.expenshare.model.entity.GroupEntity;
import com.expenshare.model.entity.UserEntity;
import com.expenshare.model.mapper.SettlementMapper;
import com.expenshare.repository.facade.ExpenseShareRepositoryFacade;
import com.expenshare.repository.facade.GroupRepositoryFacade;
import com.expenshare.repository.facade.SettlementRepositoryFacade;
import com.expenshare.repository.facade.UserRepositoryFacade;
import com.expenshare.exception.NotFoundException;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class GroupService {

    private final GroupRepositoryFacade groupRepositoryFacade;
    private final UserRepositoryFacade userRepositoryFacade;
    private final GroupMapper groupMapper;
    private final ExpenseShareRepositoryFacade expenseShareRepositoryFacade;
    private final SettlementRepositoryFacade settlementRepositoryFacade;
    private final SettlementMapper settlementMapper;
    private final KafkaProducer kafkaProducer;

    public GroupService(GroupRepositoryFacade groupRepositoryFacade,
                        UserRepositoryFacade userRepositoryFacade,
                        GroupMapper groupMapper,
                        ExpenseShareRepositoryFacade expenseShareRepositoryFacade,
                        SettlementRepositoryFacade settlementRepositoryFacade,
                        SettlementMapper settlementMapper,
                        KafkaProducer kafkaProducer) {
        this.groupRepositoryFacade = groupRepositoryFacade;
        this.userRepositoryFacade = userRepositoryFacade;
        this.groupMapper = groupMapper;
        this.expenseShareRepositoryFacade = expenseShareRepositoryFacade;
        this.settlementRepositoryFacade = settlementRepositoryFacade;
        this.settlementMapper = settlementMapper;
        this.kafkaProducer = kafkaProducer;
    }

    public GroupDto createGroup(CreateGroupRequest request) {
        // get members from ids
        List<UserEntity> members = request.getMembers().stream()
                .map(userRepositoryFacade::getOrThrow) // throws NotFoundException if missing
                .collect(Collectors.toList());

        // create the group
        GroupEntity group = new GroupEntity();
        group.setName(request.getName());
        GroupEntity savedGroup = groupRepositoryFacade.createGroup(group);

        // add members
        groupRepositoryFacade.addMembers(savedGroup, members);
        GroupEntity finalGroup = groupRepositoryFacade.findGroup(savedGroup.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found after creation"));

        // publish group created event
        List<Long> memberIds = members.stream()
                .map(UserEntity::getUserId)
                .toList();

        Map<String, Object> groupCreatedPayload = Map.of(
                "groupId", finalGroup.getGroupId(),
                "name", finalGroup.getName(),
                "memberIds", memberIds
        );

        kafkaProducer.publish(
                EventTopics.GROUP_CREATED,
                String.valueOf(finalGroup.getGroupId()),
                groupCreatedPayload
        );
        // publish notification.welcome event
        Map<String, Object> notificationPayload = Map.of(
                "targetType", "GROUP",
                "targetId", finalGroup.getGroupId(),
                "channel", "IN_APP",
                "message", "New group created: " + finalGroup.getName()
        );

        kafkaProducer.publish(
                EventTopics.NOTIFICATION_WELCOME,
                "group-" + finalGroup.getGroupId(),
                notificationPayload
        );

        return groupMapper.toDTO(finalGroup);
    }

    public GroupDto getGroup(Long groupId) {
        GroupEntity group = groupRepositoryFacade.findGroup(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found with id " + groupId));
        return groupMapper.toDTO(group);
    }

    public GroupEntity getGroupEntity(Long groupId) {
        GroupEntity group = groupRepositoryFacade.findGroup(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found with id " + groupId));
        return group;
    }

    public GroupDto addMembers(Long groupId, AddMembersRequest request) {
        // find the group
        GroupEntity group = groupRepositoryFacade.findGroup(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        // get users from ids
        List<UserEntity> users = request.getMembers().stream()
                .map(userRepositoryFacade::getOrThrow) // throws NotFoundException if any missing
                .collect(Collectors.toList());
        groupRepositoryFacade.addMembers(group, users);
        GroupEntity updatedGroup = groupRepositoryFacade.findGroup(group.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found after update"));

        return groupMapper.toDTO(updatedGroup);
    }

    public List<ShareDto> getGroupBalances(Long groupId) {
        // Get shares and settlements for the group
        List<ExpenseShareEntity> shares = expenseShareRepositoryFacade.findByGroupId(groupId);
        List<SettlementEntity> settlements = settlementRepositoryFacade.findByGroupId(groupId);

        // Calculate  balances
        Map<Long, BigDecimal> userBalances = new HashMap<>();
        for (ExpenseShareEntity share : shares) {

            userBalances.merge(share.getUser().getUserId(), share.getShareAmount(), BigDecimal::add);
        }
        for (SettlementEntity settlement : settlements) {
            userBalances.merge(settlement.getFromUser().getUserId(), settlement.getAmount().negate(), BigDecimal::add);
            userBalances.merge(settlement.getToUser().getUserId(), settlement.getAmount(), BigDecimal::add);
        }

        return userBalances.entrySet().stream()
                .map(entry -> new ShareDto(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<SettlementDto> getGroupSettlements(Long groupId) {
        List<SettlementEntity> settlements = settlementRepositoryFacade.findByGroupId(groupId);
        return settlements.stream()
                .map(settlement -> settlementMapper.toDto(settlement))
                .collect(Collectors.toList());
    }

    public SuggestionDto suggestMinimalSettlement(long groupId, RequestSuggestionDto request) {
        SuggestionDto suggestion = new SuggestionDto();
        suggestion.setGroupId(groupId);
        List<SettlementEntity> settlements = settlementRepositoryFacade.findByGroupId(groupId);
        suggestion.setSuggestions(
                settlements.stream()
                        .map(settlement -> settlementMapper.toDto(settlement))
                        .collect(Collectors.toList())
        );
        suggestion.setTotalTransfers(settlements.size());
        if (request != null && request.getStrategy() != null ) {
            suggestion.setStrategy(request.getStrategy());
        }else {
            suggestion.setStrategy(SettlementEntity.Suggestions.GREEDY_MIN_TRANSFERS);
        }
        return suggestion;
    }
}
