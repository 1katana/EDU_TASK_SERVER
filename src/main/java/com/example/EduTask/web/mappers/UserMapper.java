package com.example.EduTask.web.mappers;

import com.example.EduTask.domain.users.User;
import com.example.EduTask.web.dto.users.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    // Mapping from User to UserDto
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPasswordConfirmations(user.getPasswordConfirmations()); // Set password confirmation (same as password)

        return userDto;
    }

    // Mapping from UserDto to User
    public static User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPasswordConfirmations(userDto.getPasswordConfirmations());

//        // Set createdAt to current time for new users (this can be customized as needed)
//        if (userDto.getId() == null) {
//            user.setCreatedAt(LocalDateTime.now());
//        } else {
//            user.setCreatedAt(user.getCreatedAt());
//        }

        return user;
    }

    public static List<UserDto> toDtoList(List<User> users) {
        if (users == null) {
            return null;
        }

        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // Mapping from List<UserDto> to List<User>
    public static List<User> toEntityList(List<UserDto> userDtos) {
        if (userDtos == null) {
            return null;
        }

        return userDtos.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}