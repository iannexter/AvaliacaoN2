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
            return ResponseEntity
                    .badRequest()
                    .body("O campo 'title' não pode estar vazio.");
        }
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    
    @GetMapping
    public ResponseEntity<?> getTasks(@RequestParam(value = "status", required = false) String status) {
        if (status == null || status.isEmpty()) {
            return ResponseEntity.ok(taskService.getAllTasks());
        } else {
            return ResponseEntity.ok(taskService.getTasksByStatus(status));
        }
    }
}
