package com.felipe.user.controller;

import com.felipe.user.domain.UserModel;
import com.felipe.user.dto.UserDto;
import com.felipe.user.mapper.UserMapper;
import com.felipe.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        UserModel userModel = userService.findOneUserByName(name);
        return ResponseEntity.ok(userMapper.toDto(userModel));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserModel newUser = userService.createUser(userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDto(newUser));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(userMapper.toDtoList(users));
    }
}

