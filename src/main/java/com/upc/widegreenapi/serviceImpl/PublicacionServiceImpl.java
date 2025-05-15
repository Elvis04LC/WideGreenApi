package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.ComentarioRepository;
import com.upc.widegreenapi.repositories.PublicacionCategoriaRepository;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PublicacionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionCategoriaRepository publicacionCategoriaRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getContenido().isBlank()) {
            throw new RuntimeException("El contenido no puede estar vacío");
        }

        Publicacion publicacion = Publicacion.builder()
                .titulo(dto.getTitulo())
                .contenido(dto.getContenido())
                .fecha(LocalDateTime.now())
                .usuario(usuario)
                .build();

        Publicacion guardada = publicacionRepository.save(publicacion);
        return modelMapper.map(guardada, PublicacionDTO.class);
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
    public PublicacionDTO editarPublicacion(Long idPublicacion, String nuevoContenido) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        if (!publicacion.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para editar esta publicación");
        }

        publicacion.setContenido(nuevoContenido);
        publicacion.setFecha(LocalDateTime.now());

        Publicacion actualizada = publicacionRepository.save(publicacion);
        return modelMapper.map(actualizada, PublicacionDTO.class);
    }
    @Transactional
    public void eliminarPublicacion(Long idPublicacion) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        if (!publicacion.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("No tienes permiso para eliminar esta publicación");
        }
        System.out.println("Eliminando comentarios...");
        comentarioRepository.deleteByPublicacionId(idPublicacion);
        System.out.println("Eliminando asociaciones en publicacion_categoria...");
        publicacionCategoriaRepository.deleteByPublicacionId(idPublicacion);
        System.out.println("Eliminando la publicación...");
        publicacionRepository.delete(publicacion);

        System.out.println("Publicación eliminada correctamente.");
    }
}
