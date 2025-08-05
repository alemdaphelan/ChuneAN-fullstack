package vn.com.chunean.chunean.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileService {
    public String storeMusic(@NonNull MultipartFile file) throws IOException {
        String uploadDir = "uploads/music/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath,file.getBytes());
        return "uploads/music/" + fileName;
    }
}
