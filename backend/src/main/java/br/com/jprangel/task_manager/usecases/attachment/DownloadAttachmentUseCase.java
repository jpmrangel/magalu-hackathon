package br.com.jprangel.task_manager.usecases.attachment;

import br.com.jprangel.task_manager.usecases.FileStorageCaseManager;
import br.com.jprangel.task_manager.repository.TaskAttachmentRepository; // Repositório hipotético
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DownloadAttachmentUseCase {

    private final FileStorageCaseManager fileStorageCaseManager;
    private final TaskAttachmentRepository taskAttachmentRepository;

    public Resource execute(String fileName) throws IOException {
        return fileStorageCaseManager.loadFileAsResource(fileName);
    }
}