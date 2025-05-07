package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.OrganizadorEventoDTO;
import com.upc.widegreenapi.entities.OrganizadorEvento;
import com.upc.widegreenapi.repositories.OrganizadorEventoRepository;
import com.upc.widegreenapi.service.OrganizadorEventoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizadorEventoServiceImpl implements OrganizadorEventoService {
    @Autowired
    private OrganizadorEventoRepository organizadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrganizadorEventoDTO registrarOrganizador(OrganizadorEventoDTO dto) {
        OrganizadorEvento organizador = modelMapper.map(dto, OrganizadorEvento.class);
        return modelMapper.map(organizadorRepository.save(organizador), OrganizadorEventoDTO.class);
    }

    @Override
    public List<OrganizadorEventoDTO> listarOrganizadores() {
        return organizadorRepository.findAll().stream()
                .map(o -> modelMapper.map(o, OrganizadorEventoDTO.class))
                .collect(Collectors.toList());
    }
}

