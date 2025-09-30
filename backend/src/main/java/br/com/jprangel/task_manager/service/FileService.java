package br.com.jprangel.task_manager.service;

import br.com.jprangel.task_manager.model.FileEntity;
import br.com.jprangel.task_manager.model.TaskEntity;
import br.com.jprangel.task_manager.repository.FileRepository;
import br.com.jprangel.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final StorageService storageService;
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;

    public FileService(StorageService storageService, FileRepository fileRepository, TaskRepository taskRepository) {
        this.storageService = storageService;
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
    }

    public FileEntity store(MultipartFile file, String taskId) throws IOException {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        String fileKey = storageService.uploadFile(file);

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileKey(fileKey);
        fileEntity.setTask(task);

        return fileRepository.save(fileEntity);
    }

    public void delete(String id) {
        FileEntity fileEntity = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        storageService.deleteFile(fileEntity.getFileKey());
        fileRepository.delete(fileEntity);
    }

    public List<FileEntity> getFilesByTask(String taskId) {
        return fileRepository.findByTaskId(taskId);
    }
}
