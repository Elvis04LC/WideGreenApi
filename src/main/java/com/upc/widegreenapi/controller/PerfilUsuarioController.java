package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;
import com.upc.widegreenapi.service.PerfilUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
public class PerfilUsuarioController {
    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @PostMapping("/registrar")
    public PerfilUsuarioDTO registrarPerfil(@RequestBody PerfilUsuarioDTO dto) {
        return perfilUsuarioService.registrarPerfil(dto);
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
