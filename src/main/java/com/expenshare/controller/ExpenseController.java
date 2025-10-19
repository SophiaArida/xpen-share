package com.expenshare.controller;

import com.expenshare.model.dto.expense.CreateExpenseRequest;
import com.expenshare.model.dto.expense.ExpenseDto;
import com.expenshare.service.ExpenseService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.Optional;

@Controller("/api/expenses")
public class ExpenseController {

    @Inject
    private ExpenseService expenseService;

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ExpenseDto> createExpense(@Body @Valid CreateExpenseRequest request) {
        ExpenseDto created = expenseService.createExpense(request);
        return HttpResponse.created(created);
    }

    @Get(uri = "/{expenseId}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ExpenseDto> getExpense(@PathVariable Long expenseId) {
        Optional<ExpenseDto> expense = expenseService.getExpense(expenseId);
        return expense.map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }
}
