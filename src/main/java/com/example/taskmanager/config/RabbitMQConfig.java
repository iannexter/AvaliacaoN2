package com.example.taskmanager.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String TASK_QUEUE = "taskQueue";

    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE, true);
    }
}