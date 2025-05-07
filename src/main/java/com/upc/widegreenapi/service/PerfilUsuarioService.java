package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;

import java.util.List;

public interface PerfilUsuarioService {
    PerfilUsuarioDTO registrarPerfil(PerfilUsuarioDTO dto);
    PerfilUsuarioDTO obtenerPerfilPorId(Long id);
    List<PerfilUsuarioDTO> listarPerfiles();
    PerfilUsuarioDTO actualizarPerfil(Long id, PerfilUsuarioDTO dto);
    void eliminarPerfil(Long id);
}
