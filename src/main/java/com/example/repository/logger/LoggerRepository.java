package com.example.repository.logger;

import com.example.entity.user.User;

public interface LoggerRepository {

    void log(String fileName, User user);

}
