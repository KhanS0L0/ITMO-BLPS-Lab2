package com.example.service.interfaces.registration;

import com.example.dto.RegistrationDTO;
import com.example.entity.user.User;
import com.example.exception.UserAlreadyExistException;

public interface RegistrationService {
    User signUp(RegistrationDTO registrationDTO) throws Exception;
}
