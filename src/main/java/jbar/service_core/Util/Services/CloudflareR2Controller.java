package jbar.service_core.Util.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage")
public class CloudflareR2Controller {

    private final CloudflareR2Service storageService;

    public CloudflareR2Controller(CloudflareR2Service storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileUrl = storageService.uploadFile(file);
        return ResponseEntity.ok(fileUrl);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String fileName) {
        storageService.deleteFile(fileName);
        return ResponseEntity.ok("Archivo eliminado: " + fileName);
    }
}
