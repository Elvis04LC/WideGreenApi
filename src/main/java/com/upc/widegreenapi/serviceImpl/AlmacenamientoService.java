package com.upc.widegreenapi.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AlmacenamientoService {
    private static final String UPLOAD_DIR = "uploads/";

    public String guardarImagen(MultipartFile file) throws IOException {
        // Crear nombre único para evitar colisiones
        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path ruta = Paths.get(UPLOAD_DIR + nombreArchivo);

        // Crear la carpeta si no existe
        Files.createDirectories(ruta.getParent());

        // Guardar el archivo
        Files.write(ruta, file.getBytes());

        // Retornar URL accesible (debes exponerla vía /imagenes/**)
        return "/imagenes/" + nombreArchivo;
    }
}
