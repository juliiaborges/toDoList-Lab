package com.labdessoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Todos os detalhes sobre uma tarefa")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Descrição da tarefa deve possuir pelo menos 10 caracteres")
    @Size(min = 10, message = "Descrição da tarefa deve ter no mínimo 10 caracteres")
    private String description;
    private Boolean completed;

    private LocalDate dueDate;  // Data de conclusão para tarefas do tipo Data
    private Integer deadlineDays;  // Prazo em dias para tarefas do tipo Prazo
    private String taskType;  // Tipo de tarefa: "Data", "Prazo" ou "Livre"

    public Task(String description, Boolean completed, LocalDate dueDate, Integer deadlineDays, String taskType) {
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.deadlineDays = deadlineDays;
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return String.format("Task [id=%s, description=%s, completed=%s, dueDate=%s, deadlineDays=%s, taskType=%s]",
            id, description, completed, dueDate, deadlineDays, taskType);
    }
}
