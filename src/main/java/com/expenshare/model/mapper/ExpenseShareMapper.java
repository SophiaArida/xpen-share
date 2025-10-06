package com.expenshare.model.mapper;

import com.expenshare.model.dto.expense.ShareDto;
import com.expenshare.model.entity.ExpenseShareEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseShareMapper {
    @Mapping(target = "expenseId", source = "expenseId")
    ExpenseShareEntity toEntity(ShareDto dto, Long expenseId);
    ShareDto toDto(ExpenseShareEntity e);
}
