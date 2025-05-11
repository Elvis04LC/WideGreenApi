package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.CategoriaPublicacionDTO;
import com.upc.widegreenapi.service.CategoriaPublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaPublicacionController {

    @Autowired
    private CategoriaPublicacionService categoriaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crear")
    public ResponseEntity<CategoriaPublicacionDTO> crearCategoria(@RequestBody CategoriaPublicacionDTO dto) {
        CategoriaPublicacionDTO creada = categoriaService.crearCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaPublicacionDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoría eliminada con ID: " + id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editar")
    public ResponseEntity<CategoriaPublicacionDTO> editarCategoria(@RequestBody CategoriaPublicacionDTO dto) {
        CategoriaPublicacionDTO actualizada = categoriaService.editarCategoria(dto);
        return ResponseEntity.ok(actualizada);
    }
}
