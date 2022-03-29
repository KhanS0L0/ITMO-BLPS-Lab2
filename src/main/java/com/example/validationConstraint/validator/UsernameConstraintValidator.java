package com.example.validationConstraint.validator;

import com.example.validationConstraint.annotation.Username;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintValidator implements ConstraintValidator<Username, String> {
    @Override
    public void initialize(Username username) {
        ConstraintValidator.super.initialize(username);
    }

    @Override
    public boolean isValid(String usernameField, ConstraintValidatorContext ctx) {
        if(usernameField == null)
            return false;
        return usernameField.matches("\\b[a-zA-Z][a-zA-Z0-9]{7,31}\\b");
    }
}
