package com.andersen.service;

import com.andersen.domain.User;
import com.andersen.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    UserDto getUser(long id);

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(long id);

}
