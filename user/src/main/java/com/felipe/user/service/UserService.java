package com.felipe.user.service;

import com.felipe.user.domain.UserModel;
import com.felipe.user.producer.UserProducer;
import com.felipe.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Autowired
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
    public UserModel saveUser(UserModel userModel) {
        userModel = userRepository.save(userModel);
        userProducer.publishEvent(userModel);
        return userModel;
    }
}

