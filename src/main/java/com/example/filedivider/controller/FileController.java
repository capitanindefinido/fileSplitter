
package com.example.filedivider.controller;

import com.example.filedivider.service.EmailService;
import com.example.filedivider.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String enviarEmail(
            @RequestParam("destinatario") String destinatario,
            @RequestParam("archivoBase") String archivoBase) {

        // Buscamos los archivos en el directorio uploads/
        File uploadDir = new File("uploads");
        File[] archivosAdjuntos = uploadDir.listFiles((dir, name) -> name.startsWith(archivoBase));

        if (archivosAdjuntos == null || archivosAdjuntos.length == 0) {
            return "No se encontraron archivos para enviar.";
        }

        try {
            emailService.enviarCorreoConAdjuntos(
                    destinatario,
                    "Segmentos del archivo: " + archivoBase,
                    "Adjunto los segmentos solicitados.",
                    archivosAdjuntos
            );
            return "Correo enviado correctamente a " + destinatario;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }


    @PostMapping("/upload")
    public List<String> uploadFile(@RequestParam("file") MultipartFile file,
                                   @RequestParam("segmentSize") long segmentSize) throws IOException {
        return fileService.splitFile(file, segmentSize);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) throws IOException {
        File file = new File("uploads/" + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        byte[] content = Files.readAllBytes(file.toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(content);
    }
}
