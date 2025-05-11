package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PublicacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/crear")
    public ResponseEntity<PublicacionDTO> crearPublicacion(@RequestBody PublicacionDTO dto) {
        PublicacionDTO publicacion = publicacionService.crearPublicacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacion);
    }

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> listarPublicaciones() {
        List<PublicacionDTO> publicaciones = publicacionService.listarPublicaciones();
        return ResponseEntity.ok(publicaciones);
    }
    @PutMapping("/editar/{idPublicacion}")
    public ResponseEntity<PublicacionDTO> editarPublicacion(@PathVariable Long idPublicacion, @RequestParam String nuevoContenido) {
        PublicacionDTO actualizada = publicacionService.editarPublicacion(idPublicacion, nuevoContenido);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar/{idPublicacion}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long idPublicacion) {
        publicacionService.eliminarPublicacion(idPublicacion);
        return ResponseEntity.noContent().build();
    }
}
