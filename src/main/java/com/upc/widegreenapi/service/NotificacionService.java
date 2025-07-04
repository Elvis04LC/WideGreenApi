package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.NotificacionDTO;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.Notificacion;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;

import java.util.List;

public interface NotificacionService {
    List<NotificacionDTO> obtenerPorUsuario(Long idUsuario);

    void marcarComoVisto(Long idNotificacion);

    void crearDesdeInscripcion(Usuario usuario, Evento evento);
    void guardar(Notificacion notificacion);

    void crearDesdeComentario(Publicacion publicacion, Usuario autorComentario);
}
