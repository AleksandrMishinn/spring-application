package com.andersen.repository;


import com.andersen.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    void saveUser(User user);

    Optional<User> getUser(long id);

    void deleteUser(long id);

}
