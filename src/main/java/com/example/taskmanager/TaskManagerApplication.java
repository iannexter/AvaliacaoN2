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




//Invoke-WebRequest -Uri http://localhost:8080/tasks -Method GET



//try {
//    Invoke-WebRequest -Uri http://localhost:8080/tasks `
//        -Method POST `
//        -Headers @{ "Content-Type" = "application/json" } `
//        -Body '{"title":""}'
//} catch {
//    $response = $_.Exception.Response
//    $reader = New-Object System.IO.StreamReader($response.GetResponseStream())
//    $body = $reader.ReadToEnd()
//    Write-Host "Status Code:" $response.StatusCode.value__
//    Write-Host "Response Body:" $body
//}



//criar 10 tarefas: 

//1..10 | ForEach-Object {
//    Invoke-WebRequest -Uri http://localhost:8080/tasks `
//        -Method POST `
//        -Headers @{ "Content-Type" = "application/json" } `
//        -Body (@{ title = "Tarefa $_" } | ConvertTo-Json)
//}




//11..20 | ForEach-Object {
//    Invoke-WebRequest -Uri http://localhost:8080/tasks `
//        -Method POST `
//        -Headers @{ "Content-Type" = "application/json" } `
//        -Body (@{ title = "Tarefa $_" } | ConvertTo-Json)
//}




