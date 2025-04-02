package jbar.service_core.Util.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.io.IOException;
import java.util.UUID;

@Service
public class CloudflareR2Service {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket-name}")
    private String bucketName;

    @Value("${cloudflare.r2.endpoint}")
    private String endpoint;

    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;

    public CloudflareR2Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Método para subir el archivo y obtener la URL pública
    public String uploadFile(MultipartFile file) {
        try {
            // Generar un nombre único para el archivo
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            // Crear la solicitud de subida
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // Convertir el archivo a RequestBody
            RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());

            // Subir el archivo a Cloudflare R2
            s3Client.putObject(putObjectRequest, requestBody);

            // Generar la URL pública para acceder al archivo
            return publicUrl + "/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error al subir archivo", e);
        }
    }

    // Método para eliminar el archivo
    public void deleteFile(String fileName) {
        try {
            // Crear la solicitud de eliminación
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            // Eliminar el archivo de Cloudflare R2
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar archivo", e);
        }
    }
}
