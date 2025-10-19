package com.expenshare.model.dto.group;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Serdeable
public class CreateGroupRequest {
    @NotBlank
    private String name;
    @NotEmpty
    private List<Long> members;
    //    Getters and setters
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
}
