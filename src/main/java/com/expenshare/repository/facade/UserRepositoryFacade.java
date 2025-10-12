package com.expenshare.repository.facade;

import com.expenshare.model.entity.UserEntity;
import com.expenshare.repository.UserRepository;
import com.expenshare.exception.NotFoundException;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.util.Optional;

@Singleton
public class UserRepositoryFacade {

    private final UserRepository userRepository;

    public UserRepositoryFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ReadOnly
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
    @ReadOnly
    public UserEntity getOrThrow(Long id) throws NotFoundException{
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }
    @ReadOnly
    public boolean emailExists(String emailLower) {
        return userRepository.existsByEmailIgnoreCase(emailLower);
    }

    @Transactional
    public UserEntity create(UserEntity e) {
        e.setCreatedAt(Instant.now());
        e.setEmail(e.getEmail().toLowerCase());
        return userRepository.save(e);
    }

}
