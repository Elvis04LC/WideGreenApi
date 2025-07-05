package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.NoticiaDTO;
import com.upc.widegreenapi.service.NoticiaService;
import com.upc.widegreenapi.serviceImpl.AlmacenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/noticias")
public class NoticiaController {
    @Autowired
    private NoticiaService noticiaService;
    @Autowired
    private AlmacenamientoService almacenamientoService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public NoticiaDTO crearNoticiaConImagen(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile
    ) {
        String imagenUrl = null;
        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                imagenUrl = almacenamientoService.guardarImagen(imagenFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }

        NoticiaDTO dto = new NoticiaDTO();
        dto.setTitulo(titulo);
        dto.setContenido(contenido);
        dto.setFecha(fecha);
        dto.setImagenUrl(imagenUrl);

        return noticiaService.crearNoticia(dto);
    }


    //Listar todas las noticias
    @GetMapping
    public List<NoticiaDTO> listarNoticias() {
        return noticiaService.listarNoticias();
    }

    //Obtener noticia por ID
    @GetMapping("/{id}")
    public NoticiaDTO obtenerNoticiaPorId(@PathVariable Long id) {
        return noticiaService.obtenerNoticiaPorId(id);
    }

    //Actualizar noticia
    @PutMapping("/{id}")
    public NoticiaDTO actualizarNoticia(@PathVariable Long id, @RequestBody NoticiaDTO noticiaDTO) {
        return noticiaService.actualizarNoticia(id, noticiaDTO);
    }

    //Eliminar noticia
    @DeleteMapping("/{id}")
    public void eliminarNoticia(@PathVariable Long id) {
        noticiaService.eliminarNoticia(id);
    }

    //Filtrar noticias por fecha
    @GetMapping("/filtrar/fecha")
    public List<NoticiaDTO> filtrarNoticiasPorFecha(@RequestParam("fecha") String fecha) {
        LocalDate fechaConvertida = LocalDate.parse(fecha);
        return noticiaService.filtrarNoticiasPorFecha(fechaConvertida);
    }

    //Filtrar noticias por tema
    @GetMapping("/filtrar/tema")
    public List<NoticiaDTO> filtrarNoticiasPorTema(@RequestParam("tema") String tema) {
        return noticiaService.filtrarNoticiasPorTema(tema);
    }
}
