package com.andersen.converter;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserConverter implements ConverterToEntity<UserDto, User>, ConverterToDto<User, UserDto> {

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().toString());

        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());

        if (userDto.getId() != 0) {
            user.setId(userDto.getId());
        }

        return user;
    }
}
