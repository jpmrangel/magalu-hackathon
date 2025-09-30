package br.com.jprangel.task_manager.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponseDTO {
    private String id;
    private String name;
    private List<TaskResponseDTO> tasks;
    private LocalDateTime createdAt;
}