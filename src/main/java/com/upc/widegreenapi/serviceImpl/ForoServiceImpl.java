package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ForoDTO;
import com.upc.widegreenapi.entities.Foro;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.ForoRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.ForoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ForoServiceImpl implements ForoService {
    @Autowired
    private ForoRepository foroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Implementación del método para crear un foro
    @Override
    public ForoDTO crearForo(ForoDTO foroDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Buscar el usuario autenticado desde su email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapear el foroDTO a la entidad
        Foro foro = modelMapper.map(foroDTO, Foro.class);
        foro.setUsuario(usuario); // Asociar al usuario autenticado
        foro.setFechaCreacion(LocalDate.now()); // Usa LocalDate.now() para asignar la fecha de hoy
        Foro foroGuardado = foroRepository.save(foro);

        ForoDTO foroRespuesta = modelMapper.map(foroGuardado, ForoDTO.class);
        foroRespuesta.setNombreUsuario(usuario.getUsername()); // 👈 aquí agregamos el nombre

        return foroRespuesta;

    }

    // Implementación del método para obtener todos los foros
    @Override
    public List<ForoDTO> obtenerForos() {
        List<Foro> foros = foroRepository.findAll();
        return foros.stream().map(foro -> {
            ForoDTO dto = modelMapper.map(foro, ForoDTO.class);
            dto.setNombreUsuario(foro.getUsuario().getUsername()); // 👈 aquí seteamos el nombre
            return dto;
        }).toList(); // Usamos ModelMapper para mapear cada Foro a ForoDTO
    }

    // Implementación del método para obtener foros por usuario
    @Override
    public List<ForoDTO> obtenerForosPorUsuario(Long usuarioId) {
        List<Foro> foros = foroRepository.findByUsuario_IdUsuario(usuarioId);
        return foros.stream().map(foro -> modelMapper.map(foro, ForoDTO.class)).toList();  // Usamos ModelMapper para mapear cada Foro a ForoDTO
    }
}
