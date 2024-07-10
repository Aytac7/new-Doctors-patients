package com.example.task3jpamanytomany.exception;

public class DoctorIdNotFoundException extends RuntimeException{
    public DoctorIdNotFoundException(String code,String message){
        super(message);
    }
}
