package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.UsuarioNotFoundException;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.UsuarioService;
import org.modelmapper.ModelMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO dto) {
        logger.info("Iniciando el registro de un nuevo usuario con email: " + dto.getEmail());
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .password("123456") // temporal
                .fechaRegistro(LocalDateTime.now())
                .build();
        UsuarioDTO usuarioDTO = modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);

        logger.info("Usuario registrado exitosamente con ID: " + usuarioDTO.getIdUsuario());
        return usuarioDTO;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        logger.info("Listando todos los usuarios.");
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .collect(Collectors.toList());

        logger.info("Se han encontrado " + usuarios.size() + " usuarios.");

        return usuarios;
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        logger.info("Buscando usuario con ID: " + id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    // Logueando el error cuando no se encuentra el usuario
                    logger.severe("Usuario no encontrado con ID: " + id);
                    return new UsuarioNotFoundException("Usuario no encontrado con ID: " + id);
                });

        // Logueando el éxito de la búsqueda
        logger.info("Usuario encontrado con ID: " + usuario.getIdUsuario());

        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
