package com.example.service.interfaces.user;

import com.example.entity.user.Account;

public interface AccountService {
    Account findAccountByUsername(String username);
}
