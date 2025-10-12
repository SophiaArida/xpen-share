package com.expenshare.controller;

import com.expenshare.model.dto.group.*;
import com.expenshare.service.GroupService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import jakarta.validation.Valid;

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

    /**
     * GET /api/groups/{id}
     * Fetch group by id.
     */
    @Get("/{id}")
    public HttpResponse<GroupDto> getGroup(Long id) {
        GroupDto group = groupService.getGroup(id);
        return HttpResponse.ok(group);
    }

    /**
     * POST /api/groups/{id}/members
     * Add new members to a group.
     */
    @Post("/{id}/members")
    public HttpResponse<GroupDto> addMembers(Long id, @Body @Valid AddMembersRequest request) {
        GroupDto updatedGroup = groupService.addMembers(id, request);
        return HttpResponse.ok(updatedGroup);
    }
}
