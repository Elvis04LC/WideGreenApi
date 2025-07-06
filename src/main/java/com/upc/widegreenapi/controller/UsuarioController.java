package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.dtos.UsuarioMesDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.InvalidEmailException;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import java.util.stream.Collectors;
@Valid
@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/listar")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    @GetMapping("/autenticado")
    public UsuarioDTO obtenerUsuarioAutenticado(Authentication authentication) {
        String email = authentication.getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    @GetMapping("/usuariosPorMes")
    public ResponseEntity<List<UsuarioMesDTO>> usuariosPorMes() {
        return ResponseEntity.ok(usuarioService.cantidadUsuariosPorMes());
    }


}
