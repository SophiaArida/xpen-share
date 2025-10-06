package com.expenshare.repository;

import com.expenshare.model.entity.GroupEntity;
import com.expenshare.model.entity.GroupMemberEntity;
import com.expenshare.model.entity.UserEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends CrudRepository<GroupMemberEntity, Long> {
    boolean existsByGroupAndUser(GroupEntity group, UserEntity user);
    List<GroupMemberEntity> findByGroup(GroupEntity group);
    Optional<GroupMemberEntity> findByGroupAndUser(GroupEntity group, UserEntity user);

}
