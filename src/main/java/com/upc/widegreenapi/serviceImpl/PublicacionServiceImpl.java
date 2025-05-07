package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PublicacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO dto) {
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

    @Override
    public List<PublicacionDTO> listarPublicaciones() {
        return publicacionRepository.findAll().stream()
                .map(pub -> {
                    PublicacionDTO dto = modelMapper.map(pub, PublicacionDTO.class);
                    dto.setUsuarioEmail(pub.getUsuario().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
