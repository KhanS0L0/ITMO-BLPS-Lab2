package com.example.dto;

import com.example.validationConstraint.annotation.Password;
import com.example.validationConstraint.annotation.Username;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class RegistrationDTO {
    @Username
    private String username;

    @Email
    private String email;

    @Password
    private String password;
}
