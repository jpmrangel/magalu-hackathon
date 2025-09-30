package br.com.jprangel.task_manager.dto;

import java.time.LocalDateTime;

import br.com.jprangel.task_manager.model.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskDTO {
    @NotBlank(message = "O nome da lista é obrigatório")
    private String name;
    private String description;
    @NotNull(message = "A prioridade é obrigatória")
    private TaskPriority priority;
    @FutureOrPresent(message = "A data esperada de finalização não pode ser no passado.")
    private LocalDateTime expectedFinishingDate;
    @NotBlank(message = "O ID da lista é obrigatório")
    private String listId;
    private String reminderTime;
}
