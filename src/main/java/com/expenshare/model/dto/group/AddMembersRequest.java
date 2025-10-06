package com.expenshare.model.dto.group;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Serdeable
public class AddMembersRequest {
    @NotEmpty
    private List<Long> members;

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }
}
