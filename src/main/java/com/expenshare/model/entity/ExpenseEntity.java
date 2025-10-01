package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "expenses")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group_id;

    @ManyToOne
    @JoinColumn(name = "paid_by", nullable = false)
    private UserEntity paid_by;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "split_type", nullable = false)
    private SplitType split_type;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp created_at;

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

    public GroupEntity getGroup_id() {
        return group_id;
    }

    public void setGroup_id(GroupEntity group_id) {
        this.group_id = group_id;
    }

    public UserEntity getPaid_by() {
        return paid_by;
    }

    public void setPaid_by(UserEntity paid_by) {
        this.paid_by = paid_by;
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

    public SplitType getSplit_type() {
        return split_type;
    }

    public void setSplit_type(SplitType split_type) {
        this.split_type = split_type;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
