package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.NoticiaDTO;
import com.upc.widegreenapi.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/noticias")
public class NoticiaController {
    @Autowired
    private NoticiaService noticiaService;

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public NoticiaDTO crearNoticia(@RequestBody NoticiaDTO noticiaDTO) {
        return noticiaService.crearNoticia(noticiaDTO);
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
