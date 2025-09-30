package br.com.jprangel.task_manager.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.jprangel.task_manager.service.StorageService;

@RestController
@RequestMapping("/api/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = storageService.uploadFile(file);
            return ResponseEntity.ok(Map.of("url", fileUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to upload file"));
        }
    }

    @GetMapping("/list-files")
    public ResponseEntity<java.util.List<String>> listFiles() {
        return ResponseEntity.ok(storageService.listFiles());
    }

    @DeleteMapping("/delete-file")
    public ResponseEntity<Void> deleteFile(@RequestParam("fileKey") String fileKey) {
        storageService.deleteFile(fileKey);
        return ResponseEntity.noContent().build();
    }
}
