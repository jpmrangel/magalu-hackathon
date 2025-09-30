package br.com.jprangel.task_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jprangel.task_manager.dto.CreateTaskDTO;
import br.com.jprangel.task_manager.dto.TaskResponseDTO;
import br.com.jprangel.task_manager.dto.UpdateFinishingDateDTO;
import br.com.jprangel.task_manager.dto.UpdateTaskDTO;
import br.com.jprangel.task_manager.usecases.TaskUseCaseManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskUseCaseManager taskUseCaseManager;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody CreateTaskDTO createTask) {
        var task = taskUseCaseManager.createTask(createTask);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable String id) {
        var task = taskUseCaseManager.getTaskById(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByListId(@PathVariable String listId) {
        var tasks = taskUseCaseManager.getTasksByListId(listId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable String id, @Valid @RequestBody UpdateTaskDTO updateTask) {
        var task = taskUseCaseManager.updateTask(id, updateTask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateFinishingDate(@PathVariable String id, @Valid @RequestBody UpdateFinishingDateDTO updateTask) {
        var task = taskUseCaseManager.updateFinishingTaskDate(id, updateTask);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        taskUseCaseManager.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
