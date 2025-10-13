package com.expenshare.model.mapper;

import com.expenshare.model.dto.expense.*;
import com.expenshare.model.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ExpenseShareMapper.class)
public interface ExpenseMapper {
    @Mapping(target = "group.groupId", source = "groupId")
    ExpenseEntity toEntity(CreateExpenseRequest req);
    @Mapping(target = "expenseId", source = "e.id")
    @Mapping(target = "groupId", source = "e.group.groupId")
    ExpenseDto toDto(ExpenseEntity e, List<ShareDto> shares);
}
