package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "settlements")
public class SettlementEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long settlementId;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity groupId;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private UserEntity fromUserId;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private UserEntity toUserId;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false)
    private Method method = Method.OTHER;

    @Column(name = "note")
    private String note;

    @Column(name = "refrence")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.CONFIRMED;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "confirmed_at")
    private Instant confirmedAt;

    public enum Method {
        CASH,
        BANK_TRANSFER,
        UPI,
        OTHER
    }

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELED
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public GroupEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupEntity groupId) {
        this.groupId = groupId;
    }

    public UserEntity getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(UserEntity fromUserId) {
        this.fromUserId = fromUserId;
    }

    public UserEntity getToUserId() {
        return toUserId;
    }

    public void setToUserId(UserEntity toUserId) {
        this.toUserId = toUserId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Instant confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
