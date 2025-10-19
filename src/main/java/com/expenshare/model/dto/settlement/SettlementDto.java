package com.expenshare.model.dto.settlement;

import com.expenshare.model.entity.SettlementEntity;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.time.Instant;

@Serdeable
public class SettlementDto {
    private long settlementId;
    private long groupId;
    private long fromUserId;
    private long toUserId;
    private BigDecimal amount;
    private SettlementEntity.Method method;
    private String note;
    private SettlementEntity.Status status;
    private Instant createdAt;
    //  Getters and setters
    public long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(long settlementId) {
        this.settlementId = settlementId;
    }

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

    public SettlementEntity.Method getMethod() {
        return method;
    }

    public void setMethod(SettlementEntity.Method method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SettlementEntity.Status getStatus() {
        return status;
    }

    public void setStatus(SettlementEntity.Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
