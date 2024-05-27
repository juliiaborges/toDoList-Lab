package com.labdessoft.roteiro01.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.labdessoft.roteiro01.controller.TaskController;
import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.Priority;
import com.labdessoft.roteiro01.enums.TaskType;
import com.labdessoft.roteiro01.repository.TaskRepository;

public class TaskControllerTest {

    private TaskController taskController;
    private TaskRepository taskRepositoryMock;

    
    @BeforeEach
    void setUp() {
        taskRepositoryMock = mock(TaskRepository.class);
        taskController = new TaskController(taskRepositoryMock); // Construtor com taskRepository
    }

    @Test
    void testFindTaskById() {
        long taskId = 1L;
        Task task = new Task(taskId, "Task 1", false, TaskType.DATA, Priority.ALTA, null, null);
        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(task));

        ResponseEntity<EntityModel<Task>> response = taskController.findTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testListAll() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", false, TaskType.DATA, Priority.ALTA, null, null));
        tasks.add(new Task(2L, "Task 2", false, TaskType.DATA, Priority.BAIXA, null, null));
        when(taskRepositoryMock.findAll()).thenReturn(tasks);

        ResponseEntity<CollectionModel<EntityModel<Task>>> response = taskController.listAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks.size(), response.getBody().getContent().size());
    }

    @Test
    void testAddTask() {
        Task newTask = new Task(null, "Task 1", false, TaskType.DATA, Priority.ALTA, null, null);
        when(taskRepositoryMock.save(newTask)).thenReturn(new Task(1L, "Task 1", false, TaskType.DATA, Priority.ALTA, null, null));

        ResponseEntity<EntityModel<Task>> response = taskController.addTask(newTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateTask() {
        long taskId = 1L;
        Task updatedTask = new Task(taskId, "Updated Task", false, TaskType.DATA, Priority.MEDIA, null, null);
        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(updatedTask));
        when(taskRepositoryMock.save(updatedTask)).thenReturn(updatedTask);

        ResponseEntity<EntityModel<Task>> response = taskController.updateTask(taskId, updatedTask);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteTask() {
        long taskId = 1L;
        when(taskRepositoryMock.existsById(taskId)).thenReturn(true);

        ResponseEntity<Void> response = taskController.deleteTask(taskId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCompleteTask() {
        long taskId = 1L;
        Task existingTask = new Task(taskId, "Task 1", false, TaskType.DATA, Priority.ALTA, null, null);
        when(taskRepositoryMock.findById(taskId)).thenReturn(Optional.of(existingTask));
        existingTask.setCompleted(true);
        when(taskRepositoryMock.save(existingTask)).thenReturn(existingTask);

        ResponseEntity<EntityModel<Task>> response = taskController.completeTask(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getContent().getCompleted());
    }
}
