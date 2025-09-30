package br.com.jprangel.task_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class StorageService {

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.magalu.s3.bucket-name}")
    private String bucketName;
    
    @Value("${cloud.magalu.s3.endpoint}")
    private String endpoint;

    public String uploadFile(MultipartFile file) throws IOException {
        // Gera um nome de arquivo único para evitar sobreposições
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueFileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Retorna a URL pública do objeto
        return String.format("%s/%s/%s", endpoint, bucketName, uniqueFileName);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public java.util.List<String> listFiles() {
        java.util.List<String> fileKeys = new java.util.ArrayList<>();
        s3Client.listObjectsV2(b -> b.bucket(bucketName)).contents().forEach(obj -> fileKeys.add(obj.key()));
        return fileKeys;
    }

    public void deleteFile(String fileKey) {
        s3Client.deleteObject(b -> b.bucket(bucketName).key(fileKey));
    }
}
