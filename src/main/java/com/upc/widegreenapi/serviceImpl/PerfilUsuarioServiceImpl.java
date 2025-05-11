package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;
import com.upc.widegreenapi.entities.PerfilUsuario;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PerfilUsuarioRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PerfilUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Override
    public PerfilUsuarioDTO registrarPerfil(PerfilUsuarioDTO dto) {
        logger.info("Iniciando el registro de un nuevo perfil");

        // Obtener el email del usuario autenticado (consistente con tu CustomUserDetailsService)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        logger.info("Buscando usuario con email: " + email);

        // Buscar por email como en tu CustomUserDetailsService
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.severe("Usuario no encontrado con email: " + email);
                    return new RuntimeException("Usuario no encontrado");
                });

        logger.info("Usuario encontrado - ID: " + usuario.getIdUsuario() + ", Email: " + usuario.getEmail());

        // Verificar si el usuario ya tiene perfil
        if (perfilUsuarioRepository.existsByUsuario(usuario)) {
            throw new RuntimeException("El usuario ya tiene un perfil registrado");
        }


        PerfilUsuario perfil = new PerfilUsuario();
        perfil.setNombre(dto.getNombre());
        perfil.setApellido(dto.getApellido());
        perfil.setFoto(dto.getFoto());
        perfil.setBio(dto.getBio());
        perfil.setUsuario(usuario);  // Asocia automáticamente el usuario autenticado

        PerfilUsuario guardado = perfilUsuarioRepository.save(perfil);
        return modelMapper.map(guardado, PerfilUsuarioDTO.class);
    }

    @Override
    public PerfilUsuarioDTO obtenerPerfilPorId(Long id) {
        PerfilUsuario perfil = perfilUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
        return modelMapper.map(perfil, PerfilUsuarioDTO.class);
    }

    @Override
    public List<PerfilUsuarioDTO> listarPerfiles() {
        return perfilUsuarioRepository.findAll()
                .stream()
                .map(perfil -> modelMapper.map(perfil, PerfilUsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PerfilUsuarioDTO actualizarPerfil(Long id, PerfilUsuarioDTO dto) {
        PerfilUsuario perfil = perfilUsuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        perfil.setNombre(dto.getNombre());
        perfil.setApellido(dto.getApellido());
        perfil.setFoto(dto.getFoto());
        perfil.setBio(dto.getBio());

        return modelMapper.map(perfilUsuarioRepository.save(perfil), PerfilUsuarioDTO.class);
    }

    @Override
    public void eliminarPerfil(Long id) {
        perfilUsuarioRepository.deleteById(id);
    }
}
