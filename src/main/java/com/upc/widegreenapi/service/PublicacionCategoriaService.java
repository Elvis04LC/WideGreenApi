package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.PublicacionCategoriaDTO;

import java.util.List;

public interface PublicacionCategoriaService {
    PublicacionCategoriaDTO asociarCategoriaAPublicacion(PublicacionCategoriaDTO dto);
    List<PublicacionCategoriaDTO> listarCategoriasPorPublicacion(Long idPublicacion);
}
