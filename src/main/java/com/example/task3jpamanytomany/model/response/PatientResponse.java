package com.example.task3jpamanytomany.model.response;

import lombok.Data;

import java.util.Date;
@Data
public class PatientResponse {
    Date dob;
    Integer age;
    String name;
    String gender;
    String surname;
    String address;
    String bloodType;
    String phoneNumber;
}
