package br.com.jprangel.task_manager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String fileName;

    private String fileKey;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public void setFileName(String originalFilename) {
        this.fileName = originalFilename;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public String getFileKey() {
        return fileKey;
    }
}
