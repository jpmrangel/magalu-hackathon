package br.com.jprangel.task_manager.dto;

import java.time.LocalDateTime;

import br.com.jprangel.task_manager.model.TaskPriority;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private String id;
    private String name;
    private String description;
    private TaskPriority priority;
    private LocalDateTime expectedFinishingDate;
    private LocalDateTime finishingDate;
    private LocalDateTime createdAt;
}
