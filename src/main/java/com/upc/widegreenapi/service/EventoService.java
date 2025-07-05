package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.EventoDTO;

import java.util.List;

public interface EventoService {
    EventoDTO crearEvento(EventoDTO eventoDTO);
    List<EventoDTO> listarEventos();
    EventoDTO obtenerEventoPorId(Long id);
    EventoDTO actualizarEvento(Long id, EventoDTO eventoDTO);
    void eliminarEvento(Long id);
    List<EventoDTO> obtenerEventoPorUbicacion(String ubicacion);//Implementar metodo evento por ubicación
    List<EventoDTO> listarEventosPorUsuario(Long idUsuario);
}
