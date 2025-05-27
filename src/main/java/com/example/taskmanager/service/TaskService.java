package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Task createTask(Task task) {
        task.setStatus("pending");
        Task savedTask = taskRepository.save(task);
        rabbitTemplate.convertAndSend("taskQueue", savedTask.getId().toString());
        return savedTask;
    }
}