package com.example.task3jpamanytomany.model.request;

import com.example.task3jpamanytomany.constraint.UniqueEmail;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DoctorRequest {
    @NotBlank(message = "name can't be blank")
    String name;
    LocalDate dob;
    String address;
    String surname;
    String phoneNumber;
    String specialization;
    List<Long> patientIds;//patientIds
    @UniqueEmail
    String email;

}
