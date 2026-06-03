package com.example.loanapplication.modules.documentmodule.service.impl;



import com.example.loanapplication.modules.documentmodule.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FilestorageServiceImpl implements FileStorageService {


    private final String uploadDir = "uploads/";

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return path.toString();

        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }
    @Override
    public void deleteFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("File delete failed");
        }
    }
}
