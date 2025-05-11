package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;
import com.upc.widegreenapi.entities.ActividadCalendario;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.ActivityNotFoundException;
import com.upc.widegreenapi.exceptions.EventNotFoundException;
import com.upc.widegreenapi.repositories.ActividadCalendarioRepository;
import com.upc.widegreenapi.repositories.CalendarioRepository;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.ActividadCalendarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ActividadCalendarioServiceImpl implements ActividadCalendarioService {

    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());
    @Autowired
    private ActividadCalendarioRepository actividadCalendarioRepository;

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ActividadCalendarioDTO registrarActividad(ActividadCalendarioDTO dto) {
        logger.info("Registrando nueva actividad en el calendario ID: " + dto.getIdCalendario());
        // Obtener el email del usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        logger.info("Buscando usuario con email: " + email);


        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        logger.info("Usuario encontrado - ID: " + usuario.getIdUsuario());

        // Obtener el Calendario del usuario autenticado
        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado para el usuario autenticado"));

        logger.info("Calendario encontrado - ID: " + calendario.getId());

        // Crear la actividad
        ActividadCalendario actividad = new ActividadCalendario();
        actividad.setCalendario(calendario);
        actividad.setTitulo(dto.getTitulo());
        actividad.setFecha(dto.getFecha());
        actividad.setHora(dto.getHora());
        actividad.setDescripcion(dto.getDescripcion());

        // Validación del evento (si existe)
        if (dto.getIdEvento() != null) {
            Evento evento = eventoRepository.findById(dto.getIdEvento())
                    .orElseThrow(() -> new EventNotFoundException("Evento no encontrado con ID: " + dto.getIdEvento()));

            actividad.setEvento(evento);
        }
        ActividadCalendario actividadGuardada = actividadCalendarioRepository.save(actividad);
        ActividadCalendarioDTO respuesta = modelMapper.map(actividadGuardada, ActividadCalendarioDTO.class);

        if (actividadGuardada.getEvento() != null) {
            respuesta.setIdEvento(actividadGuardada.getEvento().getIdEvento());
            respuesta.setFechaEvento(actividadGuardada.getEvento().getFecha());
        }

        logger.info("Actividad registrada con ID: " + actividadGuardada.getId());

        return respuesta;
    }

    @Override
    public List<ActividadCalendarioDTO> listarActividadesPorCalendario(Long idCalendario) {
        List<ActividadCalendario> actividades = actividadCalendarioRepository.findByCalendarioId(idCalendario);
        if (actividades.isEmpty()) {
            throw new ActivityNotFoundException("No se encontraron actividades registradas para el calendario con ID: " + idCalendario);
        }
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

        if (actividad.getEvento() != null) {
            logger.info("La actividad está vinculada al evento ID: " + actividad.getEvento().getIdEvento());
        }

        actividadCalendarioRepository.delete(actividad);
    }
}
