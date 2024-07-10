package com.example.task3jpamanytomany.exception;

public class EmailExistsException extends RuntimeException{
    public EmailExistsException(String code, String message){
        super(message);
    }
}
