package com.example.validationConstraint.annotation;

import com.example.validationConstraint.validator.UsernameConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {
    String message() default "Username is not valid. 1) Username must be between 8 and 32 symbols " +
                                                    "2) Username must start with character" +
                                                    "3) Username must contain only characters or numbers.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
