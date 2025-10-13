package com.expenshare.model.mapper;

import com.expenshare.model.dto.expense.ShareDto;
import com.expenshare.model.entity.ExpenseShareEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseShareMapper {
    @Mapping(target = "expense.id", source = "expenseId")
    ExpenseShareEntity toEntity(ShareDto dto, Long expenseId);
    @Mapping(target = "userId", source = "e.user.userId")
    @Mapping(target = "share", source = "shareAmount")
    ShareDto toDto(ExpenseShareEntity e);
}
