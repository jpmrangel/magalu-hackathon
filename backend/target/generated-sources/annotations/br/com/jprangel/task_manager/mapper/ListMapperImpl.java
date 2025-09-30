package br.com.jprangel.task_manager.mapper;

import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.model.ListEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-30T15:08:48-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ListMapperImpl implements ListMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public ListResponseDTO toListResponseDTO(ListEntity listEntity) {
        if ( listEntity == null ) {
            return null;
        }

        ListResponseDTO listResponseDTO = new ListResponseDTO();

        listResponseDTO.setCreatedAt( listEntity.getCreatedAt() );
        listResponseDTO.setId( listEntity.getId() );
        listResponseDTO.setName( listEntity.getName() );
        listResponseDTO.setTasks( taskEntityListToTaskResponseDTOList( listEntity.getTasks() ) );

        return listResponseDTO;
    }

    protected List<TaskResponseDTO> taskEntityListToTaskResponseDTOList(List<TaskEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<TaskResponseDTO> list1 = new ArrayList<TaskResponseDTO>( list.size() );
        for ( TaskEntity taskEntity : list ) {
            list1.add( taskMapper.toTaskResponseDTO( taskEntity ) );
        }

        return list1;
    }
}
