package br.com.jprangel.task_manager.usecases.attachment;

import br.com.jprangel.task_manager.usecases.FileStorageCaseManager;
import br.com.jprangel.task_manager.repository.TaskAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AttachFileToTaskUseCase {

    private final FileStorageCaseManager fileStorageCaseManager;
    private final TaskAttachmentRepository taskAttachmentRepository;

    public String execute(MultipartFile file, Long taskId) throws IOException {

        String savedFileName = fileStorageCaseManager.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(savedFileName)
                .toUriString();

        taskAttachmentRepository.saveAttachmentReference(
                taskId,
                savedFileName,
                fileDownloadUri,
                file.getContentType(),
                file.getSize()
        );

        return fileDownloadUri;
    }
}