package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.CategoriaConteoDTO;

import java.util.List;

public interface CategoriaConteoService {
    List<CategoriaConteoDTO> cantidadPublicacionesPorCategoria();
}
