package com;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediPlanBackendApplication {
    public static void main(String[] args) {
        // Load .env file
        Dotenv dotenv = Dotenv.load();

        // Set environment variables manually
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        SpringApplication.run(MediPlanBackendApplication.class, args);
    }

}
