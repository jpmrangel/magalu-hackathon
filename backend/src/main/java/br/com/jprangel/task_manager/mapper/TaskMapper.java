package br.com.jprangel.task_manager.mapper;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.model.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponseDTO toTaskResponseDTO(TaskEntity taskEntity);
}
