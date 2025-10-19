package com.expenshare.model.dto.settlement;

import com.expenshare.model.entity.SettlementEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Serdeable
public class CreateSettlementRequest {
    @NotNull
    private long groupId;
    @NotNull
    private long fromUserId;
    @NotNull
    private long toUserId;
    @NotNull
    @Positive
    private BigDecimal amount;
    @Nullable
    private SettlementEntity.Method method;
    @Nullable
    private String note;
    @Nullable
    private String reference;
    @Nullable
    private Boolean enforcedOwedLimit;
    //  Getters and setters
    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Nullable
    public SettlementEntity.Method getMethod() {
        return method;
    }

    public void setMethod(@Nullable SettlementEntity.Method method) {
        this.method = method;
    }

    @Nullable
    public String getNote() {
        return note;
    }

    public void setNote(@Nullable String note) {
        this.note = note;
    }

    @Nullable
    public String getReference() {
        return reference;
    }

    public void setReference(@Nullable String reference) {
        this.reference = reference;
    }

    @Nullable
    public Boolean getEnforcedOwedLimit() {
        return enforcedOwedLimit;
    }

    public void setEnforcedOwedLimit(@Nullable Boolean enforcedOwedLimit) {
        this.enforcedOwedLimit = enforcedOwedLimit;
    }
}
