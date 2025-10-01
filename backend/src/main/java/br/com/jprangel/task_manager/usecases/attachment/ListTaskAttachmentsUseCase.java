package br.com.jprangel.task_manager.usecases.attachment;

import br.com.jprangel.task_manager.repository.TaskAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListTaskAttachmentsUseCase {

    private final TaskAttachmentRepository taskAttachmentRepository;
    public List<String> execute(Long taskId) {

        List<String> fileNames = taskAttachmentRepository.findAllByTaskId(taskId);

        return fileNames;
    }
}