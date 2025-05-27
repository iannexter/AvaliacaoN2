package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskConsumer {

    @Autowired
    private TaskRepository taskRepository;

    @RabbitListener(queues = "taskQueue")
    public void processTask(String taskId) {
        Long id = Long.parseLong(taskId);
        taskRepository.findById(id).ifPresent(task -> {
            try {
                System.out.println("Início do processamento da tarefa '" + task.getTitle() + "' às " + LocalDateTime.now());
                Thread.sleep(5000); // aqui esta para demorar mais um pouco
                task.setStatus("completed");
                taskRepository.save(task);
                System.out.println("Fim do processamento da tarefa '" + task.getTitle() + "' às " + LocalDateTime.now());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Erro no processamento da tarefa: " + e.getMessage());
            }
        });
    }
}
