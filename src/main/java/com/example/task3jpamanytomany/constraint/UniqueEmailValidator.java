package com.example.task3jpamanytomany.constraint;

import com.example.task3jpamanytomany.dao.entity.DoctorEntity;
import com.example.task3jpamanytomany.dao.repository.DoctorRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final DoctorRepository doctorRepository;


    public UniqueEmailValidator(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        System.out.println(email);
        Optional<DoctorEntity> byEmail = doctorRepository.findByEmail(email);
        System.out.println(byEmail);
        return byEmail.isEmpty();
    }

}
