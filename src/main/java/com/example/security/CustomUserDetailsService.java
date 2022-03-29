package com.example.security;

import com.example.entity.user.Account;
import com.example.security.BasicAuthUser.AuthUserFactory;
import com.example.service.interfaces.user.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Autowired
    public CustomUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findAccountByUsername(username);
        if(account == null){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        return AuthUserFactory.create(account);
    }
}
