package br.com.jprangel.task_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import br.com.jprangel.task_manager.dto.CreateListDTO;
import br.com.jprangel.task_manager.dto.ListResponseDTO;
import br.com.jprangel.task_manager.usecases.ListUseCaseManager;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
public class ListController {
    
    private final ListUseCaseManager listUseCaseManager;

    @PostMapping
    public ResponseEntity<ListResponseDTO> createList(@Valid @RequestBody CreateListDTO createList) {
        var list = listUseCaseManager.createList(createList);
        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ListResponseDTO>> getAllLists() {
        var lists = listUseCaseManager.getAllLists();
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListResponseDTO> getListById(@PathVariable String id) {
        var list = listUseCaseManager.getListById(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ListResponseDTO> updateList(@PathVariable String id, @Valid @RequestBody CreateListDTO updateList) {
        var list = listUseCaseManager.updateList(id, updateList);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteList(@PathVariable String id) {
        listUseCaseManager.deleteList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
