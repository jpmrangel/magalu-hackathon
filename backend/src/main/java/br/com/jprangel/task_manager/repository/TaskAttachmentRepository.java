package br.com.jprangel.task_manager.repository;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public interface TaskAttachmentRepository {

    void saveAttachmentReference(Long taskId, String fileName, String fileDownloadUri, String contentType, long size);

    AttachmentMetadata findByFileName(String fileName);

    List<AttachmentMetadata> findByTaskId(Long taskId);

    List<String> findAllByTaskId(Long taskId);

    class AttachmentMetadata {
        public String fileName;
        public Long taskId;
        public String downloadUri;
        public String contentType;
        public long size;

        public AttachmentMetadata(String fileName, Long taskId, String downloadUri, String contentType, long size) {
            this.fileName = fileName;
            this.taskId = taskId;
            this.downloadUri = downloadUri;
            this.contentType = contentType;
            this.size = size;
        }
    }
}