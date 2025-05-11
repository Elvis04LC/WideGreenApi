package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.RegisterRequestDTO;
import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.InvalidEmailException;
import com.upc.widegreenapi.exceptions.UsuarioNotFoundException;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.UsuarioService;
import org.modelmapper.ModelMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO registrarUsuario(RegisterRequestDTO dto) {

        logger.info("Iniciando el registro de un nuevo usuario con email: " + dto.getEmail());
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new InvalidEmailException("El email no puede estar vacío");
        }

        // Validación de formato de email
        String email = dto.getEmail().trim().toLowerCase();
        String emailRegex = "^(?=.{1,254}$)[A-Za-z0-9+_-]+(?:\\.[A-Za-z0-9+_-]+)*@(?:[A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";

        if (!Pattern.matches(emailRegex, email)) {
            logger.severe("Email inválido detectado: " + email);
            throw new InvalidEmailException("El formato del email es inválido");
        }
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado.");
        }
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado.");
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail().trim())
                .password(encodedPassword)
                .fechaRegistro(LocalDateTime.now())
                .role("USER") // Asignar rol por defecto
                .username(dto.getUsername()) // Asegúrate de incluir este campo
                .build();
        logger.info("Guardando usuario en la base de datos con email: " + usuario.getEmail());
        Usuario savedUser = usuarioRepository.save(usuario);
        logger.info("Usuario registrado exitosamente con ID: " + savedUser.getIdUsuario());
        UsuarioDTO usuarioDTO = modelMapper.map(savedUser, UsuarioDTO.class);
        logger.info("Usuario registrado exitosamente con ID: " + savedUser.getIdUsuario());
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
