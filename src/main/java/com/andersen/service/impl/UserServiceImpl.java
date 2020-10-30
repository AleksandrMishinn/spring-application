package com.andersen.service.impl;

import com.andersen.controller.exception.RoleNotFoundException;
import com.andersen.controller.exception.UserNotFoundException;
import com.andersen.converter.ConverterDtoToUser;
import com.andersen.converter.ConverterUserToDto;
import com.andersen.domain.Role;
import com.andersen.domain.User;
import com.andersen.dto.UserDto;
import com.andersen.repository.RoleRepository;
import com.andersen.repository.UserRepository;
import com.andersen.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ConverterUserToDto converterUserToDto;
    private final ConverterDtoToUser converterDtoToUser;

    @Override
    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(converterUserToDto::convert)
                .collect(Collectors.toList());
    }

    @Override
    @PostAuthorize("principal.getUsername() == returnObject.username or hasRole('ROLE_ADMIN')")
    public UserDto getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return converterUserToDto.convert(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User newUser = converterDtoToUser.convert(userDto);

        Role role = roleRepository.findById(Long.parseLong(userDto.getRole()))
                .orElseThrow(() -> new RoleNotFoundException(userDto.getRole()));
        newUser.setRole(role);

        userRepository.save(newUser);

        return converterUserToDto.convert(newUser);
    }

    @Override
    @PostAuthorize("principal.getUsername() == returnObject.username or hasRole('ROLE_ADMIN')")
    public UserDto updateUser(UserDto userDto) {

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException(userDto.getId()));

        user.setRole(roleRepository.findById(Long.parseLong(userDto.getRole()))
                .orElseThrow(() -> new RoleNotFoundException(userDto.getRole())));

        user.setName(userDto.getName());

        userRepository.save(user);

        return converterUserToDto.convert(user);
    }

    @Override
    @PostAuthorize("principal.getUsername() == returnObject.username or hasRole('ROLE_ADMIN')")
    public UserDto deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);

        return converterUserToDto.convert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;

    }
}
