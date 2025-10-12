package com.expenshare.repository.facade;

import com.expenshare.model.entity.SettlementEntity;
import com.expenshare.repository.SettlementRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.time.Instant;

@Singleton
public class SettlementRepositoryFacade {
    private final SettlementRepository settlementRepository;

    public SettlementRepositoryFacade(SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }
    @Transactional
    public SettlementEntity createConfirmed(SettlementEntity e) {
        e.setCreatedAt(Instant.now());
        e.setConfirmedAt(Instant.now());
        e.setStatus(SettlementEntity.Status.CONFIRMED);
        return settlementRepository.save(e);
    }

    @ReadOnly
    public BigDecimal owedAmount(long groupId, long fromUserId, long toUserId){
        //TODO: implement function
        return BigDecimal.ONE;
    }

}
