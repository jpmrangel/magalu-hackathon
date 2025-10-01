package br.com.jprangel.task_manager.controller;

import br.com.jprangel.task_manager.usecases.TaskUseCaseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileStorageController {

    private final TaskUseCaseManager taskUseCaseManager;

    @PostMapping("/upload/{taskId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long taskId) throws IOException {
        String fileDownloadUri = taskUseCaseManager.attachFileToTask(file, taskId);

        return ResponseEntity.ok("Upload realizado! Download disponível em: " + fileDownloadUri);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = taskUseCaseManager.downloadAttachment(fileName);

        String contentType = request.getServletContext().getMimeType(resource.getFilename());
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/list/{taskId}")
    public ResponseEntity<List<String>> listFiles(@PathVariable Long taskId) {
        List<String> fileNames = taskUseCaseManager.listTaskAttachments(taskId);

        return ResponseEntity.ok(fileNames);
    }
}