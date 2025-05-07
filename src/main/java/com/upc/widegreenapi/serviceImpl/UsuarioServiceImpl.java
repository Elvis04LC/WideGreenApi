package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .email(dto.getEmail())
                .password("123456") // temporal
                .fechaRegistro(LocalDateTime.now())
                .build();
        return modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
