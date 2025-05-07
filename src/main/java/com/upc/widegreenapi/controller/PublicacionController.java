package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelMapper modelMapper;

    @PostMapping("/crear")
    public PublicacionDTO crearPublicacion(@RequestBody PublicacionDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getUsuarioEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = Publicacion.builder()
                .titulo(dto.getTitulo())
                .contenido(dto.getContenido())
                .fecha(LocalDateTime.now())
                .usuario(usuario)
                .build();

        return modelMapper.map(publicacionRepository.save(publicacion), PublicacionDTO.class);
    }

    @GetMapping
    public List<PublicacionDTO> listar() {
        return publicacionRepository.findAll().stream()
                .map(pub -> {
                    PublicacionDTO dto = modelMapper.map(pub, PublicacionDTO.class);
                    dto.setUsuarioEmail(pub.getUsuario().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
