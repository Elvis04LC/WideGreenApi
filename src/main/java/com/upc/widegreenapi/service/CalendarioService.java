package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.CalendarioDTO;

import java.util.List;

public interface CalendarioService {
    CalendarioDTO crearCalendario(CalendarioDTO calendarioDTO);;
    CalendarioDTO obtenerCalendarioPorUsuario(Long idUsuario);
    List<CalendarioDTO> listarCalendarios();
    void eliminarCalendario(Long id);
}
