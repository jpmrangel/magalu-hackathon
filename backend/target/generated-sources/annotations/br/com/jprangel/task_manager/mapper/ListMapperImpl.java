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
    date = "2025-09-30T20:22:57-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
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

        listResponseDTO.setId( listEntity.getId() );
        listResponseDTO.setName( listEntity.getName() );
        listResponseDTO.setTasks( taskEntityListToTaskResponseDTOList( listEntity.getTasks() ) );
        listResponseDTO.setCreatedAt( listEntity.getCreatedAt() );

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
