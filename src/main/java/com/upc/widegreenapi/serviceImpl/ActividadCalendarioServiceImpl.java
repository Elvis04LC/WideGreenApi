package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;
import com.upc.widegreenapi.entities.ActividadCalendario;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.repositories.ActividadCalendarioRepository;
import com.upc.widegreenapi.repositories.CalendarioRepository;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.service.ActividadCalendarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadCalendarioServiceImpl implements ActividadCalendarioService {
    @Autowired
    private ActividadCalendarioRepository actividadCalendarioRepository;

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ActividadCalendarioDTO registrarActividad(ActividadCalendarioDTO dto) {
        Calendario calendario = calendarioRepository.findById(dto.getIdCalendario())
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));

        ActividadCalendario actividad = new ActividadCalendario();
        actividad.setCalendario(calendario);
        actividad.setTitulo(dto.getTitulo());
        actividad.setFecha(dto.getFecha());
        actividad.setHora(dto.getHora());
        actividad.setDescripcion(dto.getDescripcion());

        if (dto.getIdEvento() != null) {
            Evento evento = eventoRepository.findById(dto.getIdEvento())
                    .orElse(null); // Puede ser null, así que hay que manejarlo
            actividad.setEvento(evento);
        }

        ActividadCalendario actividadGuardada = actividadCalendarioRepository.save(actividad);
        ActividadCalendarioDTO respuesta = modelMapper.map(actividadGuardada, ActividadCalendarioDTO.class);

        if (actividadGuardada.getEvento() != null) {
            respuesta.setIdEvento(actividadGuardada.getEvento().getIdEvento());
            respuesta.setFechaEvento(actividadGuardada.getEvento().getFecha());
        }

        return respuesta;
    }

    @Override
    public List<ActividadCalendarioDTO> listarActividadesPorCalendario(Long idCalendario) {
        List<ActividadCalendario> actividades = actividadCalendarioRepository.findByCalendarioId(idCalendario);

        return actividades.stream().map(actividad -> {
            ActividadCalendarioDTO dto = modelMapper.map(actividad, ActividadCalendarioDTO.class);
            dto.setIdEvento(actividad.getEvento() != null ? actividad.getEvento().getIdEvento() : null);
            dto.setFechaEvento(actividad.getEvento() != null ? actividad.getEvento().getFecha() : null);
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public ActividadCalendarioDTO obtenerActividad(Long id) {
        ActividadCalendario actividad = actividadCalendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        return modelMapper.map(actividad, ActividadCalendarioDTO.class);
    }

    @Override
    public void eliminarActividad(Long id) {
        ActividadCalendario actividad = actividadCalendarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
        actividadCalendarioRepository.delete(actividad);
    }
}
