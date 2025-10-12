package com.expenshare.controller;

import com.expenshare.model.dto.settlement.CreateSettlementRequest;
import com.expenshare.model.dto.settlement.SettlementDto;
import com.expenshare.service.SettlementService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("api/settlements")
public class SettlementController {
    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @Post
    public HttpResponse<SettlementDto> createSettlement(@Body CreateSettlementRequest req) {
        SettlementDto dto = settlementService.createSettlement(req);
        return HttpResponse.created(dto);
    }

}
