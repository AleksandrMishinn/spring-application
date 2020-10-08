package com.andersen.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @NotNull
    private String name;

    @NotNull
    private String role;

    @NotNull
    private long id;

    @NotNull
    private String password;

    @NotNull
    private String username;
}
