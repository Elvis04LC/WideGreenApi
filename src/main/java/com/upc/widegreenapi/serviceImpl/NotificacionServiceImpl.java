package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.NotificacionDTO;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.Notificacion;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.NotificacionRepository;
import com.upc.widegreenapi.service.NotificacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<NotificacionDTO> obtenerPorUsuario(Long idUsuario) {
        List<Notificacion> notificaciones = notificacionRepository.findByUsuario_IdUsuarioOrderByFechaDesc(idUsuario);
        return notificaciones.stream()
                .map(n -> {
                    NotificacionDTO dto = modelMapper.map(n, NotificacionDTO.class);
                    if (n.getPublicacion() != null) {
                        dto.setIdPublicacion(n.getPublicacion().getIdPublicacion());
                        dto.setTituloPublicacion(n.getPublicacion().getTitulo());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void marcarComoVisto(Long idNotificacion) {
        Optional<Notificacion> optional = notificacionRepository.findById(idNotificacion);
        if (optional.isPresent()) {
            Notificacion noti = optional.get();
            noti.setVisto(true);
            notificacionRepository.save(noti);
        }
    }

    @Override
    public void crearDesdeInscripcion(Usuario usuario, Evento evento) {
        Notificacion noti = new Notificacion();
        noti.setUsuario(usuario);
        noti.setContenido("Te has inscrito al evento: " + evento.getNombre());
        noti.setFecha(LocalDateTime.now());
        noti.setVisto(false);
        noti.setPublicacion(null);
        notificacionRepository.save(noti);
    }

    @Override
    public void crearDesdeComentario(Publicacion publicacion, Usuario autorComentario) {
        System.out.println("✔ Entrando a crearDesdeComentario");

        Usuario autorPublicacion = publicacion.getUsuario();

        if (!autorComentario.getEmail().trim().equalsIgnoreCase(autorPublicacion.getEmail().trim())) {
            Notificacion noti = new Notificacion();
            noti.setUsuario(autorPublicacion);
            noti.setContenido("Nuevo comentario en tu publicación: " + publicacion.getTitulo());
            noti.setFecha(LocalDateTime.now());
            noti.setVisto(false);
            noti.setPublicacion(publicacion);

            notificacionRepository.save(noti);
            System.out.println("✅ Notificación creada para: " + autorPublicacion.getEmail());
        } else {
            System.out.println("❌ No se crea notificación (el autor comentó su propia publicación)");
        }
    }
    @Override
    public void guardar(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }

}
