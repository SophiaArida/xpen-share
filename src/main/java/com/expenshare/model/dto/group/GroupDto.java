package com.expenshare.model.dto.group;

import io.micronaut.serde.annotation.Serdeable;

import java.time.Instant;
import java.util.List;

@Serdeable
public class GroupDto {
    private long groupId;
    private String name;
    private List<Long> members;
    private Instant createdAt;
    //    getters and setters
    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Long> getMembers() {
        return members;
    }
    public void setMembers(List<Long> members) {
        this.members = members;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}