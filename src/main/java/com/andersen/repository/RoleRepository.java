package com.andersen.repository;

import com.andersen.domain.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> findAll();

    Optional<Role> getRole(long id);

    Optional<Role> getRole(String name);

    void saveRole(Role role);

    void deleteRole(long id);

}
