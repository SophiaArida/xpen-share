package com.expenshare.model.dto.settlement;

import com.expenshare.model.entity.SettlementEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class RequestSuggestionDto {
    private SettlementEntity.Suggestions strategy;
    private double roundingTo;

    public SettlementEntity.Suggestions getStrategy() {
        return strategy;
    }

    public void setStrategy(SettlementEntity.Suggestions strategy) {
        this.strategy = strategy;
    }

    public double getRoundingTo() {
        return roundingTo;
    }

    public void setRoundingTo(double roundingTo) {
        this.roundingTo = roundingTo;
    }
}
