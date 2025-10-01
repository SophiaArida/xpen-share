package com.expenshare.model.entity;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.AUTO;

@Serdeable
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserEntity {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Nullable
    @Column(name = "mobile_number")
    private String mobile_number;

    @Nullable
    @Column(name = "addr_line1")
    private String addr_line1;

    @Nullable
    @Column(name = "addr_line2")
    private String addr_line2;

    @Nullable
    @Column(name = "addr_city")
    private String addr_city;

    @Nullable
    @Column(name = "addr_state")
    private String addr_state;

    @Nullable
    @Column(name = "addr_postal")
    private String addr_postal;

    @Nullable
    @Column(name = "addr_country")
    private String addr_country;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(@Nullable String mobile_number) {
        this.mobile_number = mobile_number;
    }

    @Nullable
    public String getAddr_line1() {
        return addr_line1;
    }

    public void setAddr_line1(@Nullable String addr_line1) {
        this.addr_line1 = addr_line1;
    }

    @Nullable
    public String getAddr_line2() {
        return addr_line2;
    }

    public void setAddr_line2(@Nullable String addr_line2) {
        this.addr_line2 = addr_line2;
    }

    @Nullable
    public String getAddr_city() {
        return addr_city;
    }

    public void setAddr_city(@Nullable String addr_city) {
        this.addr_city = addr_city;
    }

    @Nullable
    public String getAddr_state() {
        return addr_state;
    }

    public void setAddr_state(@Nullable String addr_state) {
        this.addr_state = addr_state;
    }

    @Nullable
    public String getAddr_postal() {
        return addr_postal;
    }

    public void setAddr_postal(@Nullable String addr_postal) {
        this.addr_postal = addr_postal;
    }

    @Nullable
    public String getAddr_country() {
        return addr_country;
    }

    public void setAddr_country(@Nullable String addr_country) {
        this.addr_country = addr_country;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
