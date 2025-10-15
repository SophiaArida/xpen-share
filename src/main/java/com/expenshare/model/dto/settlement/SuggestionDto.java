package com.expenshare.model.dto.settlement;

import com.expenshare.model.entity.SettlementEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
public class SuggestionDto {
    private long groupId;
    List<SettlementDto> suggestions;
    private long totalTransfers;
    private SettlementEntity.Suggestions strategy;

    public SuggestionDto() {
    }
    public SuggestionDto(long groupId, List<SettlementDto> suggestions, long totalTransfers, SettlementEntity.Suggestions strategy) {
        this.groupId = groupId;
        this.suggestions = suggestions;
        this.totalTransfers = totalTransfers;
        this.strategy = strategy;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public List<SettlementDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SettlementDto> suggestions) {
        this.suggestions = suggestions;
    }

    public long getTotalTransfers() {
        return totalTransfers;
    }

    public void setTotalTransfers(long totalTransfers) {
        this.totalTransfers = totalTransfers;
    }

    public SettlementEntity.Suggestions getStrategy() {
        return strategy;
    }

    public void setStrategy(SettlementEntity.Suggestions strategy) {
        this.strategy = strategy;
    }
}
