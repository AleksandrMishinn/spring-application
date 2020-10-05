package com.andersen.service.impl;

import com.andersen.controller.exception.RoleNotFoundException;
import com.andersen.controller.exception.UserNotFoundException;
import com.andersen.converter.UserConverter;
import com.andersen.domain.Role;
import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import com.andersen.repository.RoleRepository;
import com.andersen.repository.UserRepository;
import com.andersen.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService { //todo null values, create method

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserConverter converter;

    @Override
    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(converter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return converter.convertToDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Role role = roleRepository.findById(Long.parseLong(userDto.getRole()))
                .orElseThrow(() -> new RoleNotFoundException(userDto.getRole()));

        User newUser = converter.convertToEntity(userDto);
        newUser.setRole(role);

        return converter.convertToDto(userRepository.save(newUser));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));

        if (userDto.getRole() != null) {
            Role role = roleRepository.findById(Long.parseLong(userDto.getRole()))
                    .orElseThrow(() -> new RoleNotFoundException(userDto.getRole()));

            user.setRole(role);
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        userRepository.save(user);

        return converter.convertToDto(user);
    }

    @Override
    public UserDto deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);

        return converter.convertToDto(user);
    }
}
