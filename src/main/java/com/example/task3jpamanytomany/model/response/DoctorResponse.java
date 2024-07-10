package com.example.task3jpamanytomany.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Builder
@Data
public class DoctorResponse {
    Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    String name;
    String email;
    String address;
    String surname;
    String phoneNumber;
    String specialization;

    public DoctorResponse(Long id, LocalDate dob, String name, String email, String address, String surname, String phoneNumber, String specialization) {
        this.id = id;
        this.dob = dob;
        this.name = name;
        this.email = email;
        this.address = address;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;


    }
    public DoctorResponse(){

    }

    public DoctorResponse(Long id,String name){
        this.id = id;
        this.name = name;

    }

}
