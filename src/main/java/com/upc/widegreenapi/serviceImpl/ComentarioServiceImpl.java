package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ComentarioDTO;
import com.upc.widegreenapi.entities.Comentario;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.ComentarioRepository;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.ComentarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ComentarioDTO crearComentario(ComentarioDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Publicacion publicacion = publicacionRepository.findById(dto.getIdPublicacion())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + dto.getIdPublicacion()));

        if (dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
            throw new RuntimeException("El contenido del comentario no puede estar vacío");
        }

        Comentario comentario = Comentario.builder()
                .contenido(dto.getContenido())
                .fecha(LocalDateTime.now())
                .usuario(usuario)
                .publicacion(publicacion)
                .build();

        Comentario guardado = comentarioRepository.save(comentario);

        ComentarioDTO respuesta = modelMapper.map(guardado, ComentarioDTO.class);
        respuesta.setAutorEmail(usuario.getEmail());

        return respuesta;
    }

    @Override
    public List<ComentarioDTO> listarComentariosPorPublicacion(Long idPublicacion) {
        Publicacion publicacion = publicacionRepository.findById(idPublicacion)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con ID: " + idPublicacion));

        return comentarioRepository.findByPublicacion(publicacion).stream()
                .map(comentario -> modelMapper.map(comentario, ComentarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO editarComentario(ComentarioDTO comentarioDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comentario comentario = comentarioRepository.findById(comentarioDTO.getIdComentario())
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + comentarioDTO.getIdComentario()));

        // Verificar si el usuario autenticado es el autor del comentario
        if (!comentario.getUsuario().equals(usuario)) {
            throw new RuntimeException("No tienes permiso para editar este comentario");
        }

        // Validar el contenido del comentario
        if (comentarioDTO.getContenido() == null || comentarioDTO.getContenido().trim().isEmpty()) {
            throw new RuntimeException("El contenido no puede estar vacío");
        }

        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setFecha(LocalDateTime.now());

        Comentario actualizado = comentarioRepository.save(comentario);

        // Convertir a DTO y asignar el autorEmail
        ComentarioDTO respuesta = modelMapper.map(actualizado, ComentarioDTO.class);
        respuesta.setAutorEmail(usuario.getEmail());

        return respuesta;
    }
    @Override
    public void eliminarComentario(Long idComentario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Comentario comentario = comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado con ID: " + idComentario));

        if (!comentario.getUsuario().equals(usuario)) {
            throw new RuntimeException("No tienes permiso para eliminar este comentario");
        }

        comentarioRepository.delete(comentario);
    }
}
