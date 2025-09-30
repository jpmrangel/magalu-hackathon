package br.com.jprangel.task_manager.mapper;

import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.model.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-30T20:42:38-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponseDTO toTaskResponseDTO(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setId( taskEntity.getId() );
        taskResponseDTO.setName( taskEntity.getName() );
        taskResponseDTO.setDescription( taskEntity.getDescription() );
        taskResponseDTO.setPriority( taskEntity.getPriority() );
        taskResponseDTO.setExpectedFinishingDate( taskEntity.getExpectedFinishingDate() );
        taskResponseDTO.setFinishingDate( taskEntity.getFinishingDate() );
        taskResponseDTO.setCreatedAt( taskEntity.getCreatedAt() );

        return taskResponseDTO;
    }
}
