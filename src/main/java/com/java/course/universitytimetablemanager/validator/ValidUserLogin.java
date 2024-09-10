package com.java.course.universitytimetablemanager.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserLoginValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserLogin {
        String message() default " User login must start with a letter and contain 5 to 29 alphanumeric characters.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}
