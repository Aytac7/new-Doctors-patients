package com.example.task3jpamanytomany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Task3JpaManyToManyApplication {

    public static void main(String[] args) {
        SpringApplication.run(Task3JpaManyToManyApplication.class, args);
    }

}
