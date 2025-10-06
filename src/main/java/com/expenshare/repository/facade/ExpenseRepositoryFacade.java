package com.expenshare.repository.facade;

import com.expenshare.model.entity.ExpenseEntity;
import com.expenshare.model.entity.ExpenseShareEntity;
import com.expenshare.repository.ExpenseRepository;
import com.expenshare.repository.ExpenseShareRepository;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ExpenseRepositoryFacade {

    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository shareRepository;

    public ExpenseRepositoryFacade(ExpenseRepository expenseRepository, ExpenseShareRepository shareRepository) {
        this.expenseRepository = expenseRepository;
        this.shareRepository = shareRepository;
    }

    @Transactional
    public ExpenseEntity saveWithShares(ExpenseEntity expense, List<ExpenseShareEntity> shares) {
        ExpenseEntity saved = expenseRepository.save(expense);
        shares.forEach(s -> s.setExpenseId(saved.getId()));
        shareRepository.saveAll(shares);
        return saved;
    }

    public Optional<ExpenseEntity> findById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<ExpenseShareEntity> findSharesByExpenseId(Long expenseId) {
        return shareRepository.findAll().stream()
                .filter(s -> s.getExpenseId().equals(expenseId))
                .toList();
    }
}
