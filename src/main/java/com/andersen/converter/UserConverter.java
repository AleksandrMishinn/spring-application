package com.andersen.converter;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import com.andersen.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserConverter implements ConverterToEntity<UserDto, User>, ConverterToDto<User, UserDto> {

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setRole(user.getRole().toString());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        return userDto;
    }

    @Override
    public User convertToEntity(UserDto userDto) {
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
