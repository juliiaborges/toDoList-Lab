package com.labdessoft.roteiro01.unit.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.enums.Priority;
import com.labdessoft.roteiro01.enums.TaskType;
import com.labdessoft.roteiro01.repository.TaskRepository;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSaveTask() {
        Task task = new Task(null, "Task Description", false, TaskType.DATA, Priority.ALTA, null, null);
        Task savedTask = taskRepository.save(task);
        assertNotNull(savedTask.getId());
    }

    @Test
    public void testFindById() {
        Task task = new Task(null, "Task Description", false, TaskType.DATA, Priority.ALTA, null, null);
        Task savedTask = taskRepository.save(task);

        Optional<Task> optionalTask = taskRepository.findById(savedTask.getId());
        assertTrue(optionalTask.isPresent());
        assertEquals(task.getDescription(), optionalTask.get().getDescription());
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task(null, "Task Description", false, TaskType.DATA, Priority.ALTA, null, null);
        Task savedTask = taskRepository.save(task);

        savedTask.setDescription("Updated Description");
        Task updatedTask = taskRepository.save(savedTask);

        assertEquals("Updated Description", updatedTask.getDescription());
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task(null, "Task Description", false, TaskType.DATA, Priority.ALTA, null, null);
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());
        assertFalse(taskRepository.existsById(savedTask.getId()));
    }
}
