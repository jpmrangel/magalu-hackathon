package br.com.jprangel.task_manager.model;

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
    @JoinColumn(name = "task_id")
    private TaskEntity task;

}
