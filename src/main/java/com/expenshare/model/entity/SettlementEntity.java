package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "settlements")
public class SettlementEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group_id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private UserEntity from_user_id;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private UserEntity to_user_id;

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
    private java.sql.Timestamp created_at;

    @Column(name = "confirmed_at")
    private java.sql.Timestamp confirmed_at;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupEntity getGroup_id() {
        return group_id;
    }

    public void setGroup_id(GroupEntity group_id) {
        this.group_id = group_id;
    }

    public UserEntity getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(UserEntity from_user_id) {
        this.from_user_id = from_user_id;
    }

    public UserEntity getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(UserEntity to_user_id) {
        this.to_user_id = to_user_id;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getConfirmed_at() {
        return confirmed_at;
    }

    public void setConfirmed_at(Timestamp confirmed_at) {
        this.confirmed_at = confirmed_at;
    }
}
