package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class TaskConsumer {
    @Autowired
    private TaskRepository taskRepository;

    @RabbitListener(queues = "taskQueue")
    public void processTask(String taskId) {
        Long id = Long.parseLong(taskId);
        taskRepository.findById(id).ifPresent(task -> {
            task.setStatus("completed");
            taskRepository.save(task);
            System.out.println("Task " + task.getTitle() + " processed!");
        });
    }
}