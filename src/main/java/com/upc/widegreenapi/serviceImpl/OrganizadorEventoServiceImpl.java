package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.OrganizadorEventoDTO;
import com.upc.widegreenapi.entities.OrganizadorEvento;
import com.upc.widegreenapi.repositories.OrganizadorEventoRepository;
import com.upc.widegreenapi.service.OrganizadorEventoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrganizadorEventoServiceImpl implements OrganizadorEventoService {
    private static final Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    @Autowired
    private OrganizadorEventoRepository organizadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrganizadorEventoDTO registrarOrganizador(OrganizadorEventoDTO dto) {
        logger.info("Intentando registrar organizador: " + dto.getNombreOrganizador());

        // Verificar si ya existe un organizador con el mismo nombre y contacto
        boolean existe = organizadorRepository.existsByNombreOrganizadorAndContacto(
                dto.getNombreOrganizador(),
                dto.getContacto()
        );

        if (existe) {
            throw new RuntimeException("Ya existe un organizador con el mismo nombre y contacto");
        }

        OrganizadorEvento organizador = modelMapper.map(dto, OrganizadorEvento.class);
        OrganizadorEvento guardado = organizadorRepository.save(organizador);

        logger.info("Organizador registrado con ID: " + guardado.getIdOrganizador());

        return modelMapper.map(guardado, OrganizadorEventoDTO.class);
    }

    @Override
    public List<OrganizadorEventoDTO> listarOrganizadores() {
        return organizadorRepository.findAll().stream()
                .map(o -> modelMapper.map(o, OrganizadorEventoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String eliminarOrganizador(Long id) {
        logger.info("Eliminando organizador con ID: " + id);

        OrganizadorEvento organizador = organizadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));

        organizadorRepository.delete(organizador);
        logger.info("Organizador eliminado");

        return "Organizador de evento eliminado";
    }
}

