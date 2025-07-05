package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.EventoDTO;
import com.upc.widegreenapi.entities.*;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.InscripcionEventoRepository;
import com.upc.widegreenapi.repositories.TipoEventoRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.EventoService;
import com.upc.widegreenapi.service.InscripcionEventoService;
import com.upc.widegreenapi.service.NotificacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InscripcionEventoRepository inscripcionEventoRepository;
    //Registrar Eventos
    @Override
    public EventoDTO crearEvento(EventoDTO dto) {
        TipoEvento tipoEvento = tipoEventoRepository.findById(dto.getIdTipoEvento())
                .orElseThrow(() -> new RuntimeException("Tipo de evento no encontrado"));

        Evento evento = Evento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .ubicacion(dto.getUbicacion())
                .fecha(dto.getFecha())
                .hora(dto.getHora()) // Agregado
                .tipoEvento(tipoEvento)
                .build();

        Evento eventoGuardado = eventoRepository.save(evento);

        // ✅ Notificar a todos los usuarios interesados (ejemplo simple: todos los usuarios del sistema)
        List<Usuario> usuarios = usuarioRepository.findAll(); // O puedes filtrar por distrito en el futuro

        for (Usuario u : usuarios) {
            Notificacion noti = new Notificacion();
            noti.setUsuario(u);
            noti.setContenido("Nuevo evento disponible: " + eventoGuardado.getNombre());
            noti.setFecha(LocalDateTime.now());
            noti.setVisto(false);
            noti.setPublicacion(null); // No está relacionada a publicación
            notificacionService.guardar(noti); // Este método debes agregar en el service
        }

        return modelMapper.map(eventoGuardado, EventoDTO.class);
    }
    //Listar eventos
    @Override
    public List<EventoDTO> listarEventos() {
        return eventoRepository.findAll().stream()
                .map(evento -> {
                    EventoDTO dto = modelMapper.map(evento, EventoDTO.class);
                    dto.setIdTipoEvento(evento.getTipoEvento().getIdTipoEvento());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    public EventoDTO obtenerEventoPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        return modelMapper.map(evento, EventoDTO.class);
    }

    //Actualizar evento
    @Override
    public EventoDTO actualizarEvento(Long id, EventoDTO eventoDTO) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        TipoEvento tipoEvento = tipoEventoRepository.findById(eventoDTO.getIdTipoEvento())
                .orElseThrow(() -> new RuntimeException("Tipo de evento no encontrado"));

        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(eventoDTO.getFecha());
        evento.setHora(eventoDTO.getHora());
        evento.setUbicacion(eventoDTO.getUbicacion());
        evento.setTipoEvento(tipoEvento);

        Evento actualizado = eventoRepository.save(evento);
        return modelMapper.map(actualizado, EventoDTO.class);
    }
    //Eliminar evento
    @Override
    public void eliminarEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    //Evento por ubicacion
    @Override
    public List<EventoDTO> obtenerEventoPorUbicacion(String ubicacion) {
        List<Evento> eventos = eventoRepository.buscarEventosPorUbicacion(ubicacion);
        return eventos.stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
    }
    public List<EventoDTO> listarEventosPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<InscripcionEvento> inscripciones = inscripcionEventoRepository.findByUsuario(usuario);

        // Obtener eventos de las inscripciones
        List<Evento> eventos = inscripciones.stream()
                .map(InscripcionEvento::getEvento)
                // Si quieres solo eventos futuros, descomenta la línea siguiente:
                //.filter(e -> !e.getFecha().isBefore(LocalDate.now()))
                .collect(Collectors.toList());

        // Convertir a DTO
        return eventos.stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
    }
}
