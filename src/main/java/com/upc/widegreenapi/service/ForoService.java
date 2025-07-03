package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.ForoDTO;

import java.util.List;

public interface ForoService {
    ForoDTO crearForo(ForoDTO foroDTO);

    // Método para obtener todos los foros
    List<ForoDTO> obtenerForos();

    // Método para obtener foros por ID de usuario
    List<ForoDTO> obtenerForosPorUsuario(Long usuarioId);
}
