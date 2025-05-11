package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.CategoriaPublicacionDTO;

import java.util.List;

public interface CategoriaPublicacionService {
    CategoriaPublicacionDTO crearCategoria(CategoriaPublicacionDTO categoriaDTO);
    List<CategoriaPublicacionDTO> listarCategorias();
    void eliminarCategoria(Long id);
    CategoriaPublicacionDTO editarCategoria(CategoriaPublicacionDTO categoriaDTO);
}
