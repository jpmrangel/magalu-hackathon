package br.com.jprangel.task_manager.dto;

import br.com.jprangel.task_manager.model.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskDTO {
    private String name;
    private String description;
    private TaskPriority priority;
    @FutureOrPresent(message = "A data esperada de finalização não pode ser no passado.")
    private LocalDateTime expectedFinishingDate;
    private LocalDateTime finishingDate;
    private String listId;
}
