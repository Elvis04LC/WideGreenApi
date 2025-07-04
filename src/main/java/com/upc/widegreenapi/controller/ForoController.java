package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.ForoDTO;
import com.upc.widegreenapi.service.ForoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foros")
@CrossOrigin
public class ForoController {
    @Autowired
    private ForoService foroService;

    // Endpoint para crear un nuevo foro
    @PostMapping("/crear")
    public ResponseEntity<ForoDTO> crearForo(@RequestBody ForoDTO foroDTO) {
        ForoDTO nuevoForo = foroService.crearForo(foroDTO);
        return ResponseEntity.ok(nuevoForo);
    }

    // Endpoint para obtener todos los foros
    @GetMapping("/todos")
    public ResponseEntity<List<ForoDTO>> obtenerForos() {
        List<ForoDTO> foros = foroService.obtenerForos();
        return ResponseEntity.ok(foros);
    }

    // Endpoint para obtener foros por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ForoDTO>> obtenerForosPorUsuario(@PathVariable Long usuarioId) {
        List<ForoDTO> foros = foroService.obtenerForosPorUsuario(usuarioId);
        return ResponseEntity.ok(foros);
    }
}
