package com.example.mapper;

import com.example.dto.RegistrationDTO;
import com.example.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {
    public User dtoToEntity(RegistrationDTO registrationDTO){
        User entity = new User();
        entity.setUsername(registrationDTO.getUsername());
        entity.setPassword(registrationDTO.getPassword());
        entity.setEmail(registrationDTO.getEmail());
        return entity;
    }
}
