package com.expenshare.model.dto.user;

import java.time.Instant;

public class UserDto {
    public Long id;
    public String name;
    public String email;
    public String mobileNumber;
    public AddressDto address;
    public Instant createdAt;
}
