package com.labdessoft.roteiro01.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.labdessoft.roteiro01.enums.Priority;
import com.labdessoft.roteiro01.enums.TaskType;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDate dueDate;  // Data de conclusão para tarefas do tipo Data
    private Integer dueDays;  // Prazo em dias para tarefas do tipo Prazo

    public String getStatus() {
        if (type == TaskType.DATA) {
            if (completed) {
                return "Concluída";
            } else if (dueDate == null) {
                return "Prevista";
            } else if (dueDate.isAfter(LocalDate.now())) {
                return "Prevista";
            } else if (dueDate.isEqual(LocalDate.now())) {
                return "Prevista";
            } else {
                long daysLate = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                return daysLate + " dia" + (daysLate != 1 ? "s" : "") + " de atraso";
            }
        } else if (type == TaskType.PRAZO) {
            if (completed) {
                return "Concluída";
            } else if (dueDays == null) {
                return "Prevista";
            } else {
                LocalDate deadline = LocalDate.now().plusDays(dueDays);
                if (deadline.isEqual(LocalDate.now())) {
                    return "Prevista";
                } else if (deadline.isAfter(LocalDate.now())) {
                    return "Prevista";
                } else {
                    long daysLate = ChronoUnit.DAYS.between(deadline, LocalDate.now());
                    return daysLate + " dia" + (daysLate != 1 ? "s" : "") + " de atraso";
                }
            }
        } else { // Livre
            return completed ? "Concluída" : "Prevista";
        }
    }
    @Override
    public String toString() {
        return "Task [id=" + id + ", description=" + description + ", status=" + getStatus() + "]";
    }
}
