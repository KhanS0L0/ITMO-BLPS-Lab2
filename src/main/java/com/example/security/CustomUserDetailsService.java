package com.example.security;

import com.example.entity.user.Account;
import com.example.repository.xml.XmlRepository;
import com.example.security.BasicAuthUser.AuthUserFactory;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final XmlRepository repository;

    @Autowired
    public CustomUserDetailsService(XmlRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findUserByUsername(username);
        if(account == null){
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        return AuthUserFactory.create(account);
    }
}
