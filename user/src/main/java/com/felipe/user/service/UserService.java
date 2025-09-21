package com.felipe.user.service;

import com.felipe.user.domain.UserModel;
import com.felipe.user.dto.UserDto;
import com.felipe.user.infra.exceptions.NotFoundException;
import com.felipe.user.infra.exceptions.ValidationException;
import com.felipe.user.producer.UserProducer;
import com.felipe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    public UserModel findOneUserByName(String name)
    {
        return userRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(
                        "O nome informado não foi encontrado no sistema.",
                        "Verifique se o username está digitado corretamente."
                ));
    }

    @Transactional
    public UserModel createUser(UserDto userDto) {
        validateUniqueName(userDto.name());
        validateUniqueEmail(userDto.email());

        var userModel = new UserModel(userDto.name(), userDto.email());

        userModel = userRepository.save(userModel);
        userProducer.publishEvent(userModel);
        return userModel;
    }

    private void validateUniqueName(String name) {
        if (userRepository.existsByName(name)) {
            throw new ValidationException(
                    "O nome informado já está sendo utilizado.",
                    "Utilize outro nome para realizar esta operação.");
        }
    }

    private void validateUniqueEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ValidationException(
                    "O email informado já está sendo utilizado.",
                    "Utilize outro email para realizar esta operação.");
        }
    }
}

