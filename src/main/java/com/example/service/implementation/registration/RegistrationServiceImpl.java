package com.example.service.implementation.registration;

import com.example.dto.RegistrationDTO;
import com.example.entity.user.Account;
import com.example.entity.user.User;
import com.example.exception.UserAlreadyExistException;
import com.example.mapper.RegistrationMapper;
import com.example.service.interfaces.registration.RegistrationService;
import com.example.service.interfaces.user.AccountService;
import com.example.service.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final AccountService accountService;
    private final UserService userService;
    private final RegistrationMapper registrationMapper;

    @Autowired
    public RegistrationServiceImpl(AccountService accountService, UserService userService, RegistrationMapper registrationMapper) {
        this.accountService = accountService;
        this.userService = userService;
        this.registrationMapper = registrationMapper;
    }

    @Override
    @Transactional
    public User signUp(RegistrationDTO registrationDTO) throws UserAlreadyExistException{
        Account account = accountService.findAccountByUsername(registrationDTO.getUsername());
        if(account != null)
            throw new UserAlreadyExistException("User with username: " + registrationDTO.getUsername() + " already exists");
        return userService.registerUser(registrationMapper.dtoToEntity(registrationDTO));
    }
}
