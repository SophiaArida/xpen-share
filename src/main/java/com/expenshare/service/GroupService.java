package com.expenshare.service;

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

    public GroupService(GroupRepositoryFacade groupRepositoryFacade,
                        UserRepositoryFacade userRepositoryFacade,
                        GroupMapper groupMapper,
                        ExpenseShareRepositoryFacade expenseShareRepositoryFacade,
                        SettlementRepositoryFacade settlementRepositoryFacade,
                        SettlementMapper settlementMapper) {
        this.groupRepositoryFacade = groupRepositoryFacade;
        this.userRepositoryFacade = userRepositoryFacade;
        this.groupMapper = groupMapper;
        this.expenseShareRepositoryFacade = expenseShareRepositoryFacade;
        this.settlementRepositoryFacade = settlementRepositoryFacade;
        this.settlementMapper = settlementMapper;
    }

    public GroupDto createGroup(CreateGroupRequest request) {
        // resolve all member IDs via the UserRepositoryFacade
        List<UserEntity> members = request.getMembers().stream()
                .map(userRepositoryFacade::getOrThrow) // throws NotFoundException if missing
                .collect(Collectors.toList());

        // create the group
        GroupEntity group = new GroupEntity();
        group.setName(request.getName());

        // persist the group
        GroupEntity savedGroup = groupRepositoryFacade.createGroup(group);

        // add members
        groupRepositoryFacade.addMembers(savedGroup, members);

        // reload with members
        GroupEntity finalGroup = groupRepositoryFacade.findGroup(savedGroup.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found after creation"));

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

        // resolve userIds via UserRepositoryFacade
        List<UserEntity> users = request.getMembers().stream()
                .map(userRepositoryFacade::getOrThrow) // throws NotFoundException if any missing
                .collect(Collectors.toList());

        // delegate membership insertion to the GroupRepositoryFacade
        groupRepositoryFacade.addMembers(group, users);

        // reload updated group
        GroupEntity updatedGroup = groupRepositoryFacade.findGroup(group.getGroupId())
                .orElseThrow(() -> new NotFoundException("Group not found after update"));

        return groupMapper.toDTO(updatedGroup);
    }

    public List<ShareDto> getGroupBalances(Long groupId) {
        // Get shares and settlements for the group
        List<ExpenseShareEntity> shares = expenseShareRepositoryFacade.findByGroupId(groupId);
        System.out.println("\n\nbye");
        List<SettlementEntity> settlements = settlementRepositoryFacade.findByGroupId(groupId);

        System.out.println("\n\n\n\nShares: " + shares);

        // Calculate  balances
        Map<Long, BigDecimal> userBalances = new HashMap<>();
        for (ExpenseShareEntity share : shares) {

            userBalances.merge(share.getUser().getUserId(), share.getShareAmount(), BigDecimal::add);
        }
        System.out.println("\n\n\n\nUser balances after shares: ");
        for (Map.Entry<Long, BigDecimal> entry : userBalances.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + ", Balance: " + entry.getValue());
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
