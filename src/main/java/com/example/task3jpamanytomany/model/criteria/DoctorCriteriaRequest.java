package com.example.task3jpamanytomany.model.criteria;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class DoctorCriteriaRequest {
    //search
    String name;
    String email;
    LocalDate dobFrom;
    LocalDate dobTo ;
    String address;
    String surname;
    String phoneNumber;
    List<Long> patientIds;

    //filter
    String specialization;


}
