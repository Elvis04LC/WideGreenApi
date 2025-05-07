package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.EventoDTO;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.TipoEvento;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.TipoEventoRepository;
import com.upc.widegreenapi.service.EventoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public EventoDTO crearEvento(EventoDTO dto) {
        TipoEvento tipoEvento = tipoEventoRepository.findById(dto.getIdTipoEvento())
                .orElseThrow(() -> new RuntimeException("Tipo de evento no encontrado"));

        Evento evento = Evento.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .ubicacion(dto.getUbicacion())
                .fecha(dto.getFecha())
                .tipoEvento(tipoEvento)
                .build();

        return modelMapper.map(eventoRepository.save(evento), EventoDTO.class);
    }

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
}
