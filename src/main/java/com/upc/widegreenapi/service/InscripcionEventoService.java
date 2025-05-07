package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.InscripcionEventoDTO;

import java.util.List;

public interface InscripcionEventoService {
    InscripcionEventoDTO registrarInscripcion(InscripcionEventoDTO inscripcionEventoDTO);
    void cancelarInscripcion(Long idInscripcion);
    List<InscripcionEventoDTO> listarInscripcionesPorUsuario(Long idUsuario);
}
