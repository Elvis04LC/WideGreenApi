package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    ComentarioDTO crearComentario(ComentarioDTO comentarioDTO);
    List<ComentarioDTO> listarComentariosPorPublicacion(Long idPublicacion);
    ComentarioDTO editarComentario(ComentarioDTO comentarioDTO);
    void eliminarComentario(Long idComentario);
}
