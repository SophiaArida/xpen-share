package com.expenshare.model.dto.expense;

import com.expenshare.model.entity.ExpenseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Serdeable
public class ExpenseDto {
    private Long expenseId;
    private Long groupId;
    private Long paidBy;
    private BigDecimal amount;
    private String description;
    private List<ShareDto> split;
    private Instant createdAt;
    @NotNull
    private ExpenseEntity.SplitType splitType;

    // Getters and setters

    public Long getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getGroupId() {
        return groupId;
    }
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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

    public List<ShareDto> getSplit() {
        return split;
    }
    public void setSplit(List<ShareDto> split) {
        this.split = split;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public ExpenseEntity.SplitType getSplitType() {
        return splitType;
    }
    public void setSplitType(ExpenseEntity.SplitType splitType) {
        this.splitType = splitType;
    }
}
