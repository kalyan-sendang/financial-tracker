package com.project.financialtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinancialtrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinancialtrackerApplication.class, args);
    }

}
