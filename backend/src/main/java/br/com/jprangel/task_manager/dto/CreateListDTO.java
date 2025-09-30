package br.com.jprangel.task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateListDTO {
    @NotBlank(message = "O nome da lista é obrigatório")
    private String name;
}
