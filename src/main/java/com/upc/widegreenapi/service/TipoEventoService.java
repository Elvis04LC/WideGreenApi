package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.TipoEventoDTO;

import java.util.List;

public interface TipoEventoService {
    TipoEventoDTO registrarTipoEvento(TipoEventoDTO dto);
    List<TipoEventoDTO> listarTipoEventos();
}
