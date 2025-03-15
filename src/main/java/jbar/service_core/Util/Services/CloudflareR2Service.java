package jbar.service_core.Util.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.util.UUID;
import java.io.IOException;

@Service
public class CloudflareR2Service {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    @Value("${cloudflare.r2.endpoint}")
    private String endpoint;

    public CloudflareR2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        try {
            // Generar un nombre único
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Crear la solicitud de subida
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // Convertir InputStream a RequestBody
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            // Subir el archivo
            s3Client.putObject(putObjectRequest, requestBody);

            // Generar la URL manualmente (Cloudflare no soporta getUrl())
            return endpoint + "/" + bucketName + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo", e);
        }
    }

    public void deleteFile(String fileName) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build());
    }
}
