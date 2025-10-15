package com.expenshare.repository;

import com.expenshare.model.entity.SettlementEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface SettlementRepository extends CrudRepository<SettlementEntity, Long> {
    @Query("SELECT s FROM SettlementEntity s WHERE s.group.id = :groupId")
    public List<SettlementEntity> findByGroupId(Long groupId);
}
