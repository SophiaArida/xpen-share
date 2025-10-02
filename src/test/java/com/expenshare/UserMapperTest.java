package com.expenshare;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.expenshare.model.dto.user.*;
import com.expenshare.model.entity.UserEntity;
import com.expenshare.model.mapper.UserMapper;
@MicronautTest
class UserMapperTest {

    @Inject
    UserMapper mapper;
    @Test
    void testToEntityAndBack() {
        AddressDto address = new AddressDto();
        address.postalCode = "street 1";
        address.city = "Giza";
        address.state = "Egypt";

        CreateUserRequest req = new CreateUserRequest();
        req.name = "Bob";
        req.email = "bob@example.com";
        req.mobileNumber = "987654321";
        req.address = address;

        UserEntity entity = mapper.toEntity(req);
        assertEquals("Bob", entity.getName());
        assertEquals("Giza", entity.getAddress().getCity());

//        CreateUserRequest req = new CreateUserRequest();
//        req.name = "Marwan Emad";
//        req.email = "marwan@example.com";
//        req.mobileNumber = "201234567890";
//
//        AddressDto address = new AddressDto();
//        address.addr_line1 = "123 King Fahd Rd";
////        address.addr_line2;
//        address.addr_city = "Riyadh";
////        address.addr_state;
//        address.addr_postal = "11564";
//        req.address = address;
//
//
//        // Convert to entity
//        UserEntity entity = mapper.toEntity(req);
//        assertEquals("Marwan Emad", entity.getName());
//        assertEquals("marwan@example.com", entity.getEmail());
//        assertEquals("123 King Fahd Rd", entity.getAddress().getAddr_line1());
//
//        // Convert back to DTO
//        UserDto dto = mapper.toDto(entity);
//        assertEquals("Marwan Emad", dto.name);
//        assertEquals("marwan@example.com", dto.email);
//        assertEquals("Riyadh", dto.address.addr_city);
    }
}
