package com.example.taskmanager;


import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TaskIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3.12-management");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", () -> rabbitmq.getAmqpPort());
    }

    @Test
    void testCreateTaskAndSendToQueue() throws InterruptedException {
        Task newTask = new Task();
        newTask.setTitle("Tarefa de Integração");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> request = new HttpEntity<>(newTask, headers);

        ResponseEntity<Task> response = restTemplate.postForEntity("http://localhost:" + port + "/tasks", request, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Task createdTask = response.getBody();
        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotNull();

        // Esperar o processamento em 10 segundos
        int tentativas = 0;
        Task processedTask = null;
        while (tentativas < 20) {
            Optional<Task> maybeTask = taskRepository.findById(createdTask.getId());
            if (maybeTask.isPresent() && "completed".equals(maybeTask.get().getStatus())) {
                processedTask = maybeTask.get();
                break;
            }
            Thread.sleep(500);
            tentativas++;
        }

        assertThat(processedTask).isNotNull();
        assertThat(processedTask.getStatus()).isEqualTo("completed");
    }
}
