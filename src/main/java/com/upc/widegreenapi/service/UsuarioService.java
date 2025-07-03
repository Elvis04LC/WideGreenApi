package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.RegisterRequestDTO;
import com.upc.widegreenapi.dtos.UsuarioDTO;
import com.upc.widegreenapi.entities.Usuario;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO registrarUsuario(RegisterRequestDTO dto);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO obtenerUsuarioPorId(Long id);
    UsuarioDTO obtenerUsuarioPorEmail(String email);

}
