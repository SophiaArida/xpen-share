package com.expenshare.repository.facade;

import com.expenshare.model.entity.GroupEntity;
import com.expenshare.repository.GroupMemberRepository;
import com.expenshare.repository.GroupRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import com.expenshare.model.entity.GroupMemberEntity;
import com.expenshare.model.entity.UserEntity;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class GroupRepositoryFacade {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupRepositoryFacade(GroupRepository groupRepository,
                                 GroupMemberRepository groupMemberRepository) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    @Transactional
    public GroupEntity createGroup(GroupEntity group) {

        group.setCreatedAt(Instant.now());
        return groupRepository.save(group);
    }
    @ReadOnly
    public Optional<GroupEntity> findGroup(Long groupId) {
        return groupRepository.findById(groupId);
    }

    @ReadOnly
    public boolean groupExists(Long groupId) {
        return groupRepository.existsById(groupId);
    }

    @ReadOnly
    public List<GroupMemberEntity> getGroupMembers(GroupEntity group) {
        return groupMemberRepository.findByGroup(group);
    }

    @Transactional
    public void addMembers(GroupEntity group, List<UserEntity> users) {
        Set<Long> existingUserIds = new HashSet<>();
        for (GroupMemberEntity member : groupMemberRepository.findByGroup(group)) {
            existingUserIds.add(member.getUser().getUserId());
        }

        for (UserEntity user : users) {
            if (!existingUserIds.contains(user.getUserId())) {
                GroupMemberEntity gm = new GroupMemberEntity(group, user);
                gm.setAddedAt(Instant.now());
                groupMemberRepository.save(gm);
            }
        }
    }

    @ReadOnly
    public boolean isMember(GroupEntity group, UserEntity user) {
        return groupMemberRepository.existsByGroupAndUser(group, user);
    }
}
