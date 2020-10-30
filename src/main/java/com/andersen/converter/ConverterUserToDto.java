package com.andersen.converter;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class ConverterUserToDto implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().toString());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        return userDto;
    }
}
