package com.example.springtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringtaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringtaskApplication.class, args);
    }

}
