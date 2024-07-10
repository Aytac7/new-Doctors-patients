package com.example.task3jpamanytomany.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@Email
@NotBlank(message = "Please enter email")
@Documented
public @interface UniqueEmail {

    String message() default "Email is already in use";
    Class <?>[] groups() default {};
    Class<?extends Payload>[] payload() default{};
}