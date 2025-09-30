package br.com.jprangel.task_manager.mapper;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.model.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-30T17:17:16-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponseDTO toTaskResponseDTO(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setCreatedAt( taskEntity.getCreatedAt() );
        taskResponseDTO.setDescription( taskEntity.getDescription() );
        taskResponseDTO.setExpectedFinishingDate( taskEntity.getExpectedFinishingDate() );
        taskResponseDTO.setFinishingDate( taskEntity.getFinishingDate() );
        taskResponseDTO.setId( taskEntity.getId() );
        taskResponseDTO.setName( taskEntity.getName() );
        taskResponseDTO.setPriority( taskEntity.getPriority() );

        return taskResponseDTO;
    }
}
