
package com.example.filedivider.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.scheduling.annotation.Scheduled;

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

    @Scheduled(fixedRate = 3600000) // Cada 1 hora (en milisegundos)
    public void limpiarArchivosTemporales() {
        File carpeta = new File("uploads");
        if (!carpeta.exists()) {
            System.out.println("La carpeta uploads no existe. Nada que limpiar.");
            return;
        }

        File[] archivos = carpeta.listFiles();
        if (archivos == null) {
            System.out.println("No se encontraron archivos en la carpeta uploads.");
            return;
        }

        int archivosEliminados = 0;

        for (File archivo : archivos) {
            if (archivo.isFile()) {
                boolean eliminado = archivo.delete();
                if (eliminado) {
                    archivosEliminados++;
                    System.out.println("Archivo eliminado: " + archivo.getName());
                } else {
                    System.out.println("No se pudo eliminar: " + archivo.getName());
                }
            }
        }

        System.out.println("Limpieza completada. Archivos eliminados: " + archivosEliminados);
    }

}
