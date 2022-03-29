package com.example.validationConstraint.annotation;

import com.example.validationConstraint.validator.PasswordConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password is not valid. 1) Password must be between 8 and 32 symbols " +
                                                    "2) Password must contain at least one digit" +
                                                    "3) Password must contain at least one lowercase and uppercase Latin character" +
                                                    "4) Password must contain at least one special character like ! @ # & ( )";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
