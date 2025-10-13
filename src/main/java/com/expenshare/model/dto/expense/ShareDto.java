package com.expenshare.model.dto.expense;

import io.micronaut.serde.annotation.Serdeable;
import java.math.BigDecimal;

@Serdeable
public class ShareDto {
    private Long userId;
    private BigDecimal share;

    // getters and setters

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getShare() {
        return share;
    }
    public void setShare(BigDecimal share) {
        this.share = share;
    }
}
