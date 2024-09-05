package com.example.usermanagementapp2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return password.length() >= 8
                && password.matches(".*[A-Z].*")  // At least one uppercase letter
                && password.matches(".*[a-z].*")  // At least one lowercase letter
                && password.matches(".*\\d.*");   // At least one number
    }
}
