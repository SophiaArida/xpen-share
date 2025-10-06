package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "group_id")
    private Long groupId;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // ✅ Correct relationship mapping
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<GroupMemberEntity> members = new HashSet<>();

    // ✅ Constructors
    public GroupEntity() {}
    public GroupEntity(String name) {
        this.name = name;
    }

    // ✅ Getters & Setters
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Set<GroupMemberEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<GroupMemberEntity> members) {
        this.members = members;
    }
}
