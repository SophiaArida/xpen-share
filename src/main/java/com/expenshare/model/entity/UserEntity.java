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
    private Long userId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Nullable
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Embedded
    @Column(name = "address")
    private AddressEmbeddable address;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private java.sql.Timestamp createdAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(@Nullable String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public AddressEmbeddable getAddress() {
        return address;
    }

    public void setAddress(AddressEmbeddable address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    @Embeddable
    public static class AddressEmbeddable{
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String postalCode;
        private String country;

        public String getLine1() {
            return line1;
        }
        public void setLine1(String line1) {
            this.line1 = line1;
        }
        public String getLine2() {
            return line2;
        }
        public void setLine2(String line2) {
            this.line2 = line2;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public String getState() {
            return state;
        }
        public void setState(String state) {
            this.state = state;
        }
        public String getPostalCode() {
            return postalCode;
        }
        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
    }
   }
