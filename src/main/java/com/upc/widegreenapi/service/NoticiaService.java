package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.NoticiaDTO;

import java.time.LocalDate;
import java.util.List;

public interface NoticiaService {
    NoticiaDTO crearNoticia(NoticiaDTO noticiaDTO);
    NoticiaDTO obtenerNoticiaPorId(Long id);
    List<NoticiaDTO> listarNoticias();
    List<NoticiaDTO> filtrarNoticiasPorFecha(LocalDate fecha);
    List<NoticiaDTO> filtrarNoticiasPorTema(String tema);
    NoticiaDTO actualizarNoticia(Long id, NoticiaDTO noticiaDTO);
    void eliminarNoticia(Long id);
}
