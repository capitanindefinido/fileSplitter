
package com.example.filedivider.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<String> splitFile(MultipartFile multipartFile, long segmentSize) throws IOException {
        List<String> fileParts = new ArrayList<>();

        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdir();

        String originalFilename = multipartFile.getOriginalFilename();
        String baseName = originalFilename != null ?
                originalFilename.substring(0, originalFilename.lastIndexOf('.')) : "file";
        String extension = originalFilename != null ?
                originalFilename.substring(originalFilename.lastIndexOf('.')) : "";

        try (InputStream inputStream = multipartFile.getInputStream()) {
            byte[] buffer = new byte[(int) segmentSize];
            int bytesRead;
            int partNumber = 0;

            while ((bytesRead = inputStream.read(buffer)) > 0) {
                String partFileName = baseName + "." + partNumber + extension;
                File partFile = new File(uploadDir, partFileName);

                try (FileOutputStream out = new FileOutputStream(partFile)) {
                    out.write(buffer, 0, bytesRead);
                }

                fileParts.add(partFileName);
                partNumber++;
            }
        }

        return fileParts;
    }
}
