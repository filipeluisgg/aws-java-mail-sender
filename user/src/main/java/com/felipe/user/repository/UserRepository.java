package com.felipe.user.repository;

import com.felipe.user.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>
{
    /**
     * Optimally check if a user with the specified name already exists.
     * @param username The name of the user to be verified.
     * @return true if a user with the specified name already exists, false otherwise.
     */
    boolean existsByName(String username);

    /**
     * Optimally check if a user with the specified email already exists.
     * @param email The email to be verified.
     * @return true if a user with the specified email already exists, false otherwise.
     */
    boolean existsByEmail(String email);


    /**
     * Optimally check if a user with the specified name already exists and return the user.
     * @param name The name of the user to be verified.
     * @return Optional<UserModel> if a user with the specified name already exists.
     */
    Optional<UserModel> findByName(String name);
}

