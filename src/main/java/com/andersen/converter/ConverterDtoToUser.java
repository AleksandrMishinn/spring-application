package com.andersen.converter;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConverterDtoToUser implements Converter <UserDto, User> {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User convert(UserDto userDto) {
        User user = new User();

        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());

        if (userDto.getId() != 0) {
            user.setId(userDto.getId());
        }

        String encodedPass = bCryptPasswordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPass);
        user.setPasswordConfirm(encodedPass);

        return user;
    }
}
