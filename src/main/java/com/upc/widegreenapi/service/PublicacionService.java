package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.PublicacionDTO;

import java.util.List;

public interface PublicacionService {
    PublicacionDTO crearPublicacion(PublicacionDTO dto);
    List<PublicacionDTO> listarPublicaciones();
}
