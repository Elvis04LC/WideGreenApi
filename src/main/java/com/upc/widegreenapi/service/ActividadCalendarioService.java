package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;

import java.util.List;

public interface ActividadCalendarioService {
    ActividadCalendarioDTO registrarActividad(ActividadCalendarioDTO dto);
    List<ActividadCalendarioDTO> listarActividadesPorCalendario(Long idCalendario);
    ActividadCalendarioDTO obtenerActividad(Long idActividad);
    void eliminarActividad(Long idActividad);
}
