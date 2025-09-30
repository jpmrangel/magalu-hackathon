package br.com.jprangel.task_manager.mapper;

import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.model.ListEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface ListMapper {
    ListResponseDTO toListResponseDTO(ListEntity listEntity);
}