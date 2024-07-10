package com.example.task3jpamanytomany.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException{
    public NotFoundException(HttpStatus code, String message){
        super(message);
    }
}
