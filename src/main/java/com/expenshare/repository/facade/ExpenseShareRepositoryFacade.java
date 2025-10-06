package com.expenshare.repository.facade;

import com.expenshare.model.entity.ExpenseShareEntity;
import com.expenshare.repository.ExpenseShareRepository;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class ExpenseShareRepositoryFacade {

    private final ExpenseShareRepository repository;

    public ExpenseShareRepositoryFacade(ExpenseShareRepository repository) {
        this.repository = repository;
    }

    public List<ExpenseShareEntity> findByExpenseId(Long expenseId) {
        return repository.findAll().stream()
                .filter(s -> s.getExpenseId().equals(expenseId))
                .toList();
    }

    public List<ExpenseShareEntity> saveAll(List<ExpenseShareEntity> shares) {
        return (List<ExpenseShareEntity>) repository.saveAll(shares);
    }

    public void deleteByExpenseId(Long expenseId) {
        repository.findAll().forEach(s -> {
            if (s.getExpenseId().equals(expenseId)) {
                repository.delete(s);
            }
        });
    }
}
