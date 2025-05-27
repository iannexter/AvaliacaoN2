package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}


//para mandar dados via powersheel administrador:
//
//Invoke-WebRequest -Uri http://localhost:8080/tasks `
//    -Method POST `
//    -Headers @{ "Content-Type" = "application/json" } `
//    -Body '{"title":"Test Task"}'
