package com.example.filedivider.service;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileServiceTest {

    private final FileService fileService = new FileService();

    @Test
    public void testSplitFileDivideCorrectamente() throws IOException {
        // Arrange: Simulamos un archivo de texto
        String contenido = "Este es un archivo de prueba para tests unitarios en Java.";
        byte[] contenidoBytes = contenido.getBytes();

        MockMultipartFile archivoMock = new MockMultipartFile(
                "file",
                "prueba.txt",
                "text/plain",
                contenidoBytes
        );

        // Definimos tamaño de segmento pequeño (10 bytes)
        long tamanoSegmento = 10;

        // Act: Ejecutamos la división del archivo
        List<String> partes = fileService.splitFile(archivoMock, tamanoSegmento);

        // Assert: Esperamos que haya (contenido.length / tamanoSegmento) + 1 partes
        int expectedPartes = (int) Math.ceil((double) contenidoBytes.length / tamanoSegmento);
        assertEquals(expectedPartes, partes.size());

        // Validación de nombres de archivos (opcional)
        assertTrue(partes.get(0).startsWith("prueba.0"));
    }

    @Test
    public void testSplitFileArchivoVacio() throws IOException {
        // Arrange
        MockMultipartFile archivoMock = new MockMultipartFile(
                "file",
                "vacio.txt",
                "text/plain",
                new byte[0]
        );

        // Act
        List<String> partes = fileService.splitFile(archivoMock, 10);

        // Assert
        assertEquals(0, partes.size());
    }
}
