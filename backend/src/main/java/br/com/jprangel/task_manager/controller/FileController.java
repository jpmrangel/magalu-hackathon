package br.com.jprangel.task_manager.controller;

import br.com.jprangel.task_manager.model.FileEntity;
import br.com.jprangel.task_manager.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("taskId") String taskId) throws IOException {
        return ResponseEntity.ok(fileService.store(file, taskId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<FileEntity>> getFilesByTask(@PathVariable String taskId) {
        return ResponseEntity.ok(fileService.getFilesByTask(taskId));
    }
}
