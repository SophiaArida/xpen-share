package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "groups")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp created_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
