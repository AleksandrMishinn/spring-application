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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserConverter converter;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map((user) -> converter.convertToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(long id) {
        Optional<User> userOptional = userRepository.getUser(id);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException(id));
        return converter.convertToDto(user);
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        User newUser = converter.convertToEntity(userDto);
        Optional<Role> userRole = roleRepository.getRole(userDto.getRole());
        Role role = userRole.orElseThrow(() -> new RoleNotFoundException(userDto.getRole()));
        newUser.setRole(role);

        userRepository.saveUser(newUser);
    }

    @Override
    public void updateUser(UserDto userDto) {

        Optional<User> userOptional = userRepository.getUser(userDto.getId());
        User user = userOptional.orElseThrow(() -> new UserNotFoundException(userDto.getId()));

        if (userDto.getRole() != null) {
            Optional<Role> userRole = roleRepository.getRole(userDto.getRole());
            Role role = userRole.orElseThrow(() -> new RoleNotFoundException(userDto.getRole()));
            user.setRole(role);
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        userRepository.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        Optional<User> userOptional = userRepository.getUser(id);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteUser(id);
    }

}
