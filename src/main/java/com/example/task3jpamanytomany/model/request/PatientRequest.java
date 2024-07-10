package com.example.task3jpamanytomany.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PatientRequest {
    int age;
    Date dob;
    String gender;
    String surname;
    String address;
    String bloodType;
    String phoneNumber;
    List<Long> doctorIds;

    @NotBlank(message = "name required")
    String name;
}
