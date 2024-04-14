package com.labdessoft.roteiro01.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.labdessoft.roteiro01.entity.Task;
import com.labdessoft.roteiro01.repository.TaskRepository;

import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/task")
    @Operation(summary = "Lista todas as tarefas da lista")
    public ResponseEntity<CollectionModel<EntityModel<Task>>> listAll() {
        List<EntityModel<Task>> tasks = taskRepository.findAll().stream()
            .map(task -> EntityModel.of(task,
                linkTo(methodOn(TaskController.class).findTaskById(task.getId())).withRel("self"),
                linkTo(methodOn(TaskController.class).updateTask(task.getId(), task)).withRel("update"),
                linkTo(methodOn(TaskController.class).deleteTask(task.getId())).withRel("delete")))
            .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(CollectionModel.of(tasks, linkTo(methodOn(TaskController.class).listAll()).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "Procura uma tarefa através do ID")
    public ResponseEntity<EntityModel<Task>> findTaskById(@PathVariable("id") long id) {
        Optional<Task> taskData = taskRepository.findById(id);

        return taskData.map(task -> EntityModel.of(task,
            linkTo(methodOn(TaskController.class).findTaskById(id)).withSelfRel(),
            linkTo(methodOn(TaskController.class).updateTask(id, task)).withRel("update"),
            linkTo(methodOn(TaskController.class).deleteTask(id)).withRel("delete")))
            .map(ResponseEntity::ok)
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/task")
    @Operation(summary = "Inclui uma tarefa na lista")
    public ResponseEntity<EntityModel<Task>> addTask(@RequestBody Task task) {
        Task newTask = taskRepository.save(new Task(task.getId(), task.getDescription(), task.getCompleted()));
        return ResponseEntity.created(linkTo(methodOn(TaskController.class).findTaskById(newTask.getId())).toUri())
            .body(EntityModel.of(newTask,
                linkTo(methodOn(TaskController.class).findTaskById(newTask.getId())).withSelfRel(),
                linkTo(methodOn(TaskController.class).updateTask(newTask.getId(), newTask)).withRel("update"),
                linkTo(methodOn(TaskController.class).deleteTask(newTask.getId())).withRel("delete")));
    }

    @PutMapping("/task/{id}")
@Operation(summary = "Atualiza uma tarefa na lista através do ID")
public ResponseEntity<EntityModel<Task>> updateTask(@PathVariable("id") long id, @RequestBody Task task) {
    Optional<Task> taskData = taskRepository.findById(id);

    return taskData.map(existingTask -> {
        if (task.getDescription() != null) existingTask.setDescription(task.getDescription());
        if (task.getCompleted() != null) existingTask.setCompleted(task.getCompleted());
        taskRepository.save(existingTask);
        return EntityModel.of(existingTask,
            linkTo(methodOn(TaskController.class).findTaskById(id)).withSelfRel(),
            linkTo(methodOn(TaskController.class).updateTask(id, existingTask)).withRel("update"),
            linkTo(methodOn(TaskController.class).deleteTask(id)).withRel("delete"));
    }).map(ResponseEntity::ok)
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
}

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Deleta uma tarefa pelo ID")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.accepted().build();
        }
    }
}
