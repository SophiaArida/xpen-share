package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @Column(name = "paid_by", nullable = false)
    private Long paidBy;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "split_type", nullable = false)
    private SplitType splitType;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public enum SplitType {
        EQUAL,
        EXACT,
        PERCENT
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }
    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public Long getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(Long paidBy) {
        this.paidBy = paidBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
