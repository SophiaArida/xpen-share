package com.expenshare.service;

import com.expenshare.model.dto.group.AddMembersRequest;
import com.expenshare.model.dto.group.GroupDto;
import com.expenshare.model.dto.group.CreateGroupRequest;
import com.expenshare.model.mapper.GroupMapper;
import com.expenshare.model.entity.GroupEntity;
import com.expenshare.model.entity.UserEntity;
import com.expenshare.repository.facade.GroupRepositoryFacade;
import com.expenshare.repository.facade.UserRepositoryFacade;
import com.expenshare.exception.NotFoundException;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class GroupService {

    private final GroupRepositoryFacade groupRepositoryFacade;
    private final UserRepositoryFacade userRepositoryFacade;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepositoryFacade groupRepositoryFacade,
                        UserRepositoryFacade userRepositoryFacade,
                        GroupMapper groupMapper) {
        this.groupRepositoryFacade = groupRepositoryFacade;
        this.userRepositoryFacade = userRepositoryFacade;
        this.groupMapper = groupMapper;
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
}
