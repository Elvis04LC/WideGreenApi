package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;
import com.upc.widegreenapi.dtos.InscripcionEventoDTO;
import com.upc.widegreenapi.dtos.InscritosPorEventoDTO;
import com.upc.widegreenapi.entities.*;
import com.upc.widegreenapi.repositories.*;
import com.upc.widegreenapi.service.ActividadCalendarioService;
import com.upc.widegreenapi.service.InscripcionEventoService;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InscripcionEventoServiceImpl implements InscripcionEventoService {
    @Autowired
    private InscripcionEventoRepository inscripcionEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private NotificacionRepository notificacionRepository;
    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private ActividadCalendarioService actividadCalendarioService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InscripcionEventoDTO registrarInscripcion(InscripcionEventoDTO inscripcionEventoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento evento = eventoRepository.findById(inscripcionEventoDTO.getIdEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Verificar si el usuario ya está inscrito en este evento
        Optional<InscripcionEvento> inscripcionExistente = inscripcionEventoRepository.findByUsuarioAndEvento(usuario, evento);

        if (inscripcionExistente.isPresent()) {
            // Si ya está inscrito, eliminamos la inscripción y la actividad del calendario
            inscripcionEventoRepository.delete(inscripcionExistente.get());
            eliminarActividadDelCalendario(usuario, evento);
            throw new RuntimeException("Inscripción cancelada. Ya estaba inscrito y fue eliminado.");
        }

        // Verificar o crear el calendario del usuario
        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Calendario nuevoCalendario = new Calendario();
                    nuevoCalendario.setUsuario(usuario);
                    return calendarioRepository.save(nuevoCalendario);
                });

        // Crear la inscripción
        InscripcionEvento inscripcion = InscripcionEvento.builder()
                .usuario(usuario)
                .evento(evento)
                .fechaInscripcion(LocalDateTime.now())
                .build();

        InscripcionEvento guardado = inscripcionEventoRepository.save(inscripcion);
        Notificacion notificacion = Notificacion.builder()
                .usuario(usuario)
                .contenido("Te has inscrito correctamente al evento: " + evento.getNombre())
                .fecha(LocalDateTime.now())
                .visto(false)
                .build();

        notificacionRepository.save(notificacion);
        // Registrar la actividad en el calendario

        return modelMapper.map(guardado, InscripcionEventoDTO.class);
    }
    private void eliminarActividadDelCalendario(Usuario usuario, Evento evento) {
        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));

        List<ActividadCalendarioDTO> actividades = actividadCalendarioService.listarActividadesPorCalendario(calendario.getId());

        actividades.stream()
                .filter(actividad -> actividad.getTitulo().equals(evento.getNombre()))
                .forEach(actividad -> actividadCalendarioService.registrarActividad(actividad));
    }

    @Override
    public void cancelarInscripcion(Long idInscripcion) {
        InscripcionEvento inscripcion = inscripcionEventoRepository.findById(idInscripcion)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        Usuario usuario = inscripcion.getUsuario();
        Evento evento = inscripcion.getEvento();

        inscripcionEventoRepository.delete(inscripcion);
        eliminarActividadDelCalendario(usuario, evento);
    }

    @Override
    public List<InscripcionEventoDTO> listarInscripcionesPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return inscripcionEventoRepository.findByUsuario(usuario)
                .stream()
                .map(inscripcion -> modelMapper.map(inscripcion, InscripcionEventoDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<InscritosPorEventoDTO> mostrarCantidadInscripcionesPorEvento()
    {
        List<Tuple> verInscripcionesDeCadaEvento = inscripcionEventoRepository.obtenerTotalInscritosSegunEvento();
        List<InscritosPorEventoDTO> totalInscritos = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Tuple tuple : verInscripcionesDeCadaEvento) {
            String nombre = tuple.get(0, String.class);
            Long totalI = tuple.get(1, Long.class);
            InscritosPorEventoDTO inscripcionEventoDTO = new InscritosPorEventoDTO(nombre,totalI);
            totalInscritos.add(inscripcionEventoDTO);
        }
        return totalInscritos;
    }
}
