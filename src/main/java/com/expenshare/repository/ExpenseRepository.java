package com.expenshare.repository;

import com.expenshare.model.entity.ExpenseEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ExpenseRepository extends CrudRepository<ExpenseEntity, Long> {
}
