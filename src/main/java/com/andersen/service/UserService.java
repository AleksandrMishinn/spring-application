package com.andersen.service;

import com.andersen.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto getUser(long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto deleteUser(long id);

}
