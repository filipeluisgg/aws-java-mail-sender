package com.felipe.user.service;

import com.felipe.user.domain.UserModel;
import com.felipe.user.infra.exceptions.ValidationError;
import com.felipe.user.producer.UserProducer;
import com.felipe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }


    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public UserModel createUser(UserModel userModel) {
        validateUniqueUsername(userModel.getName());
        validateUniqueEmail(userModel.getEmail());

        userModel = userRepository.save(userModel);
        userProducer.publishEvent(userModel);
        return userModel;
    }

    private void validateUniqueUsername(String username) {
        if (userRepository.existsByName(username)) {
            throw ValidationError.builder()
                    .message("O username informado já está sendo utilizado.")
                    .action("Utilize outro username para realizar esta operação.")
                    .build();
        }
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw ValidationError.builder()
                    .message("O email informado já está sendo utilizado.")
                    .action("Utilize outro email para realizar esta operação.")
                    .build();
        }
    }
}

