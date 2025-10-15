package com.expenshare.repository;

import com.expenshare.model.entity.ExpenseShareEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
@Repository
public interface ExpenseShareRepository extends CrudRepository<ExpenseShareEntity, Long> {
    @Query("SELECT s FROM ExpenseShareEntity s INNER JOIN ExpenseEntity e ON s.expense.id = e.id WHERE e.group.groupId = :groupId")
    List<ExpenseShareEntity> findByGroupId(Long groupId);

}
