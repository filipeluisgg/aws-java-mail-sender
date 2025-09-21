package com.felipe.user.mapper;

import com.felipe.user.domain.UserModel;
import com.felipe.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper
{
    public UserDto toDto(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        return new UserDto(userModel.getName(), userModel.getEmail());
    }


    public UserModel toModel(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return new UserModel(userDto.name(), userDto.email());
    }

    public List<UserDto> toDtoList(List<UserModel> userModels) {
        return userModels.stream()
                .map(this::toDto).
                collect(Collectors.toList());
    }
}

