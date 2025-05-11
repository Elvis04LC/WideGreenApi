package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PublicacionCategoriaDTO;
import com.upc.widegreenapi.service.PublicacionCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicacion-categoria")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicacionCategoriaController {
    @Autowired
    private PublicacionCategoriaService publicacionCategoriaService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/asociar")
    public ResponseEntity<PublicacionCategoriaDTO> asociarCategoria(@RequestBody PublicacionCategoriaDTO dto) {
        PublicacionCategoriaDTO asociada = publicacionCategoriaService.asociarCategoriaAPublicacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(asociada);
    }

    @GetMapping("/publicacion/{idPublicacion}")
    public ResponseEntity<List<PublicacionCategoriaDTO>> listarCategoriasPorPublicacion(@PathVariable Long idPublicacion) {
        return ResponseEntity.ok(publicacionCategoriaService.listarCategoriasPorPublicacion(idPublicacion));
    }
}
