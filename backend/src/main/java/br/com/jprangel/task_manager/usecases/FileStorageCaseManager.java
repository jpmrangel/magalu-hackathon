package br.com.jprangel.task_manager.usecases;

import br.com.jprangel.task_manager.config.FileStorageProperties;
import br.com.jprangel.task_manager.repository.TaskAttachmentRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageCaseManager {

    private final Path fileStorageLocation;
    private final TaskAttachmentRepository taskAttachmentRepository;

    public FileStorageCaseManager(FileStorageProperties fileStorageProperties, TaskAttachmentRepository taskAttachmentRepository) {
        this.taskAttachmentRepository = taskAttachmentRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Não foi possível criar o diretório de armazenamento.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new RuntimeException("Nome de arquivo contém sequência de caminho inválida: " + fileName);
        }

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Falha ao armazenar o arquivo " + fileName, ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Erro ao carregar o arquivo: " + fileName, ex);
        }
    }

    public List<String> listTaskAttachments(Long taskId) {
        return taskAttachmentRepository.findAllByTaskId(taskId);
    }

    public List<String> listAllPhysicalFiles() {
        try (Stream<Path> paths = Files.list(this.fileStorageLocation)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Falha ao listar os arquivos fisicamente.", ex);
        }
    }
}