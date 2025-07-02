package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;
import com.upc.widegreenapi.entities.PerfilUsuario;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PerfilUsuarioRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PerfilUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
public class PerfilUsuarioController {
    @Autowired
    private PerfilUsuarioService perfilUsuarioService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;
    @PostMapping("/registrar")
    public PerfilUsuarioDTO registrarPerfil(@RequestBody PerfilUsuarioDTO dto) {
        return perfilUsuarioService.registrarPerfil(dto);
    }
    @GetMapping("/autenticado")
    public ResponseEntity<PerfilUsuarioDTO> obtenerPerfilAutenticado() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Obtenemos el email del usuario autenticado
        System.out.println("Usuario autenticado: " + email);

        // Buscar el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar el perfil por el usuario
        PerfilUsuario perfil = perfilUsuarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));

        // Mapear a DTO
        PerfilUsuarioDTO dto = modelMapper.map(perfil, PerfilUsuarioDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public PerfilUsuarioDTO obtenerPerfil(@PathVariable Long id) {
        return perfilUsuarioService.obtenerPerfilPorId(id);
    }

    @GetMapping
    public List<PerfilUsuarioDTO> listarPerfiles() {
        return perfilUsuarioService.listarPerfiles();
    }

    @PutMapping("/{id}")
    public PerfilUsuarioDTO actualizarPerfil(@PathVariable Long id, @RequestBody PerfilUsuarioDTO dto) {
        return perfilUsuarioService.actualizarPerfil(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarPerfil(@PathVariable Long id) {
        perfilUsuarioService.eliminarPerfil(id);
    }
}
