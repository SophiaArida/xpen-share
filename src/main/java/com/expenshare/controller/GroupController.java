package com.expenshare.controller;

import com.expenshare.model.dto.expense.ShareDto;
import com.expenshare.model.dto.group.*;
import com.expenshare.model.dto.settlement.RequestSuggestionDto;
import com.expenshare.model.dto.settlement.SettlementDto;
import com.expenshare.model.dto.settlement.SuggestionDto;
import com.expenshare.service.GroupService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

import java.util.List;

@Controller("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Post
    public HttpResponse<GroupDto> createGroup(@Body @Valid CreateGroupRequest request) {
        GroupDto group = groupService.createGroup(request);
        return HttpResponse.created(group);
    }

    @Get("/{id}")
    public HttpResponse<GroupDto> getGroup(Long id) {
        GroupDto group = groupService.getGroup(id);
        return HttpResponse.ok(group);
    }

    @Post("/{id}/members")
    public HttpResponse<GroupDto> addMembers(Long id, @Body @Valid AddMembersRequest request) {
        GroupDto updatedGroup = groupService.addMembers(id, request);
        return HttpResponse.ok(updatedGroup);
    }

    @Get("/{groupId}/balances")
    public HttpResponse<List<ShareDto>> getGroupBalances(Long groupId) {
        List<ShareDto> balances = groupService.getGroupBalances(groupId);
        return HttpResponse.ok(balances);
    }

    @Get("/{groupId}/settlements")
    public HttpResponse<List<SettlementDto>> getGroupSettlements(Long groupId) {
        List<SettlementDto> settlements = groupService.getGroupSettlements(groupId);
        return HttpResponse.ok(settlements);
    }

    @Post("/{groupId}/settlements/suggest")
    public HttpResponse<SuggestionDto> suggestSettlements(Long groupId, @Nullable @Body RequestSuggestionDto request) {
        SuggestionDto suggestion = groupService.suggestMinimalSettlement(groupId,request);
        return HttpResponse.ok(suggestion);
    }
}
