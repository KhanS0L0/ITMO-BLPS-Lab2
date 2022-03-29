package com.example.service.interfaces.user;

import com.example.entity.user.User;

public interface UserService {
    User findUserByUsername(String username);

    User findUserById(Long id);

    User registerUser(User user);
}
