package com.java.course.universitytimetablemanager.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginValidator implements ConstraintValidator<ValidUserLogin, String> {

    private static final String USER_LOGIN_VALIDATION_FORM = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,29}$";


    @Override
    public void initialize(ValidUserLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userLogin, ConstraintValidatorContext constraintValidatorContext) {
        if (userLogin == null) {
            return false;
        }
        return validateUserLogin(userLogin);
    }

    private boolean validateUserLogin(String userLogin) {
        Pattern pattern = Pattern.compile(USER_LOGIN_VALIDATION_FORM);
        Matcher matcher = pattern.matcher(userLogin);
        return matcher.matches();
    }
}
