package com.expenshare.model.dto.expense;

import com.expenshare.model.entity.ExpenseEntity;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Serdeable
public class CreateExpenseRequest {

    @NotNull
    private Long groupId;

    @NotNull
    private Long paidBy;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private String description;

    @Nullable
    private List<ShareDto> shares;

    @NotNull
    private ExpenseEntity.SplitType splitType;

    // Getters and setters
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getPaidBy() { return paidBy; }
    public void setPaidBy(Long paidBy) { this.paidBy = paidBy; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ShareDto> getShares() { return shares; }
    public void setShares(List<ShareDto> shares) { this.shares = shares; }

    public ExpenseEntity.SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(ExpenseEntity.SplitType splitType) {
        this.splitType = splitType;
    }
}
