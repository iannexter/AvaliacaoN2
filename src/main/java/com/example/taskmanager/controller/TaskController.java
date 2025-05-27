package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            // Retornará Bad Request 400 se der erro
            return ResponseEntity
                    .badRequest()
                    .body("O campo 'title' não pode estar vazio.");
        }
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

}