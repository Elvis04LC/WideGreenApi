package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.EventoOrganizadorDTO;

import java.util.List;

public interface EventoOrganizadorService {
    EventoOrganizadorDTO registrar(EventoOrganizadorDTO dto);
    List<EventoOrganizadorDTO> listar();
    void eliminar(Long id);
    List<EventoOrganizadorDTO> listarPorEvento(Long idEvento);
}
