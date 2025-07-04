package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.ComentarioDTO;
import com.upc.widegreenapi.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin
public class ComentarioController {
    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/crear")
    public ResponseEntity<ComentarioDTO> crearComentario(@RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO comentario = comentarioService.crearComentario(comentarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
    }

    @GetMapping("/publicacion/{idPublicacion}")
    public ResponseEntity<List<ComentarioDTO>> listarComentariosPorPublicacion(@PathVariable Long idPublicacion) {
        List<ComentarioDTO> comentarios = comentarioService.listarComentariosPorPublicacion(idPublicacion);
        return ResponseEntity.ok(comentarios);
    }

    @PutMapping("/editar")
    public ResponseEntity<ComentarioDTO> editarComentario(@RequestBody ComentarioDTO dto) {
        ComentarioDTO comentarioActualizado = comentarioService.editarComentario(dto);
        return ResponseEntity.ok(comentarioActualizado);
    }

    @DeleteMapping("/eliminar/{idComentario}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long idComentario) {
        comentarioService.eliminarComentario(idComentario);
        return ResponseEntity.noContent().build();
    }
}
