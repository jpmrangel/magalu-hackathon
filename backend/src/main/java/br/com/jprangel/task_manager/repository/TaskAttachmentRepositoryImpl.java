package br.com.jprangel.task_manager.repository;

import org.springframework.stereotype.Component;
import br.com.jprangel.task_manager.repository.TaskAttachmentRepository.AttachmentMetadata;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskAttachmentRepositoryImpl implements TaskAttachmentRepository {

    private final List<AttachmentMetadata> storage = new ArrayList<>();

    @Override
    public void saveAttachmentReference(Long taskId, String fileName, String fileDownloadUri, String contentType, long size) {
        storage.add(new AttachmentMetadata(fileName, taskId, fileDownloadUri, contentType, size));
    }

    @Override
    public AttachmentMetadata findByFileName(String fileName) {
        return storage.stream()
                .filter(m -> m.fileName.equals(fileName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AttachmentMetadata> findByTaskId(Long taskId) {
        return storage.stream()
                .filter(m -> m.taskId.equals(taskId))
                .toList();
    }

    @Override
    public List<String> findAllByTaskId(Long taskId) {
        return storage.stream()
                .filter(m -> m.taskId.equals(taskId))
                .map(m -> m.fileName)
                .toList();
    }
}