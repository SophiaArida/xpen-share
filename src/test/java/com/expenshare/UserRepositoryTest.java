package com.expenshare;

import com.expenshare.model.entity.UserEntity;
import com.expenshare.repository.UserRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest
public class UserRepositoryTest {
    @Inject
    UserRepository userRepository;

    @Test
    void testEmailExists() {
        UserEntity u = new UserEntity();
        u.setName("Alice");
        u.setEmail("alice@example.com");
        userRepository.save(u);

        assertTrue(userRepository.existsByEmailIgnoreCase("ALICE@example.com"));
        assertFalse(userRepository.existsByEmailIgnoreCase("bob@example.com"));
    }
}
