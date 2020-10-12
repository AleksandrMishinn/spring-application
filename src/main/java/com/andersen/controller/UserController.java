package com.andersen.controller;

import com.andersen.dto.Response;
import com.andersen.dto.UserDto;
import com.andersen.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<?> getUsers() {
        return Response.builder()
                .status(0)
                .message("Ok")
                .data(userService.findAll())
                .build();
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.username")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<UserDto> getUser(@PathVariable Long id) {
        return Response.<UserDto>builder()
                .status(0)
                .message("Ok")
                .data(userService.getUser(id))
                .build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<UserDto> createUser(@RequestBody UserDto userDto) {
        return Response.<UserDto>builder()
                .status(0)
                .data(userService.createUser(userDto))
                .message("Ok")
                .build();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<UserDto> updateUser(@RequestBody UserDto userDto) {
        return Response.<UserDto>builder()
                .status(0)
                .data(userService.updateUser(userDto))
                .message("Ok")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response<UserDto> deleteUser(@PathVariable Long id) {
        return Response.<UserDto>builder()
                .status(0)
                .data(userService.deleteUser(id))
                .message("Ok")
                .build();
    }
}
