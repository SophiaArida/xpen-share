package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "group_members", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "user_id"}))
public class GroupMemberEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user_id;

    @NotNull
    @Column(name = "added_at", nullable = false)
    private java.sql.Timestamp added_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupEntity getGroup_id() {
        return group_id;
    }

    public void setGroup_id(GroupEntity group_id) {
        this.group_id = group_id;
    }

    public UserEntity getUser_id() {
        return user_id;
    }

    public void setUser_id(UserEntity user_id) {
        this.user_id = user_id;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
