package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.EventoOrganizadorDTO;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.EventoOrganizador;
import com.upc.widegreenapi.entities.OrganizadorEvento;
import com.upc.widegreenapi.repositories.EventoOrganizadorRepository;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.OrganizadorEventoRepository;
import com.upc.widegreenapi.service.EventoOrganizadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoOrganizadorServiceImpl implements EventoOrganizadorService {
    @Autowired
    private EventoOrganizadorRepository repositorio;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private OrganizadorEventoRepository organizadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EventoOrganizadorDTO registrar(EventoOrganizadorDTO dto) {
        Evento evento = eventoRepository.findById(dto.getIdEvento())
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        OrganizadorEvento organizador = organizadorRepository.findById(dto.getIdOrganizador())
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));

        EventoOrganizador entidad = new EventoOrganizador();
        entidad.setEvento(evento);
        entidad.setOrganizador(organizador);

        EventoOrganizador guardado = repositorio.save(entidad);
        return convertirADTO(guardado);
    }

    @Override
    public List<EventoOrganizadorDTO> listar() {
        return repositorio.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    @Override
    public List<EventoOrganizadorDTO> listarPorEvento(Long idEvento) {
        return repositorio.findByEvento_IdEvento(idEvento)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private EventoOrganizadorDTO convertirADTO(EventoOrganizador entidad) {
        EventoOrganizadorDTO dto = new EventoOrganizadorDTO();
        dto.setId(entidad.getId());
        dto.setIdEvento(entidad.getEvento().getIdEvento());
        dto.setIdOrganizador(entidad.getOrganizador().getIdOrganizador());
        return dto;
    }
}
