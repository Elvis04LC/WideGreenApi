package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.EventoDTO;

import java.util.List;

public interface EventoService {
    EventoDTO crearEvento(EventoDTO dto);
    List<EventoDTO> listarEventos();
}
