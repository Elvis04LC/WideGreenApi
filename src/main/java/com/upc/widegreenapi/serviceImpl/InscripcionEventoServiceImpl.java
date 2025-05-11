package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;
import com.upc.widegreenapi.dtos.InscripcionEventoDTO;
import com.upc.widegreenapi.dtos.InscritosPorEventoDTO;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.InscripcionEvento;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.CalendarioRepository;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.InscripcionEventoRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.ActividadCalendarioService;
import com.upc.widegreenapi.service.InscripcionEventoService;
import jakarta.persistence.Tuple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CalendarioRepository calendarioRepository;

    @Autowired
    private ActividadCalendarioService actividadCalendarioService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InscripcionEventoDTO registrarInscripcion(InscripcionEventoDTO inscripcionEventoDTO) {

        Usuario usuario = usuarioRepository.findById(inscripcionEventoDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Evento evento = eventoRepository.findById(inscripcionEventoDTO.getIdEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        // Verificar si el usuario ya está inscrito en este evento
        Optional<InscripcionEvento> inscripcionExistente = inscripcionEventoRepository.findByUsuarioAndEvento(usuario, evento);

        if (inscripcionExistente.isPresent()) {
            // Si ya está inscrito, cancelamos la inscripción y eliminamos la actividad del calendario
            inscripcionEventoRepository.delete(inscripcionExistente.get());
            eliminarActividadDelCalendario(usuario, evento);
            throw new RuntimeException("Inscripción cancelada. Ya estaba inscrito y fue eliminado.");
        }

        // Verificar si el usuario tiene un calendario, si no, crearlo
        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseGet(() -> {
                    Calendario nuevoCalendario = new Calendario();
                    nuevoCalendario.setUsuario(usuario);
                    return calendarioRepository.save(nuevoCalendario);
                });

        // Crear la inscripción
        InscripcionEvento inscripcion = new InscripcionEvento();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        InscripcionEvento guardado = inscripcionEventoRepository.save(inscripcion);

        // Crear la actividad en el calendario
        registrarActividadEnCalendario(calendario, evento);

        return modelMapper.map(guardado, InscripcionEventoDTO.class);
    }

    private void registrarActividadEnCalendario(Calendario calendario, Evento evento) {
        ActividadCalendarioDTO actividadDTO = new ActividadCalendarioDTO();
        actividadDTO.setIdCalendario(calendario.getId());
        actividadDTO.setTitulo(evento.getNombre());
        actividadDTO.setFecha(evento.getFecha());
        actividadDTO.setHora(evento.getHora());
        actividadDTO.setDescripcion(evento.getDescripcion());

        actividadCalendarioService.registrarActividad(actividadDTO);
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
