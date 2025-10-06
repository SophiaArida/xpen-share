package com.expenshare.model.mapper;

import com.expenshare.model.dto.expense.*;
import com.expenshare.model.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "jsr330", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpenseMapper {
    ExpenseEntity toEntity(CreateExpenseRequest req);
    ExpenseDto toDto(ExpenseEntity e, List<ShareDto> shares);
}
