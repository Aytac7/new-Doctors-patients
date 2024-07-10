package com.example.task3jpamanytomany.exception;

public class PatientIdNotFoundException extends RuntimeException{
    public PatientIdNotFoundException(String code, String message){
        super(message);
    }
}
