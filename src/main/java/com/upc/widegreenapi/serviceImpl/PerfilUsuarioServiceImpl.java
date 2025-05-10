package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;
import com.upc.widegreenapi.entities.PerfilUsuario;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PerfilUsuarioRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PerfilUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilUsuarioServiceImpl implements PerfilUsuarioService {
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PerfilUsuarioDTO registrarPerfil(PerfilUsuarioDTO dto) {
        // Obtener el usuario autenticado
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
