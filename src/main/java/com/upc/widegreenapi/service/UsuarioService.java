package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO registrarUsuario(UsuarioDTO dto);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerUsuarioPorId(Long id);
}
