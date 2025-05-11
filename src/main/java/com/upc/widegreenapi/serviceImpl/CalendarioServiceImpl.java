package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.CalendarioDTO;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.CalendarioRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.CalendarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CalendarioServiceImpl implements CalendarioService {

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CalendarioDTO crearCalendario(CalendarioDTO calendarioDTO) {
        logger.info("Iniciando la creación de un nuevo calendario");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        logger.info("Buscando usuario con email: " + email);

        // Buscar el usuario autenticado
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.severe("Usuario no encontrado con email: " + email);
                    return new RuntimeException("Usuario no encontrado");
                });

        logger.info("Usuario encontrado - ID: " + usuario.getIdUsuario() + ", Email: " + usuario.getEmail());

        // Crear el calendario y asignarlo al usuario autenticado
        Calendario calendario = Calendario.builder()
                .usuario(usuario)
                .build();

        Calendario guardado = calendarioRepository.save(calendario);

        logger.info("Calendario creado con ID: " + guardado.getId() + " al usuario con nombre: " + guardado.getUsuario().getUsername());

        return modelMapper.map(guardado, CalendarioDTO.class);
    }

    @Override
    public CalendarioDTO obtenerCalendarioPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));

        return modelMapper.map(calendario, CalendarioDTO.class);
    }

    @Override
    public List<CalendarioDTO> listarCalendarios() {
        return calendarioRepository.findAll().stream()
                .map(calendario -> modelMapper.map(calendario, CalendarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarCalendario(Long id) {
        calendarioRepository.deleteById(id);
    }
}
