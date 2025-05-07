package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.TipoEventoDTO;
import com.upc.widegreenapi.entities.TipoEvento;
import com.upc.widegreenapi.repositories.TipoEventoRepository;
import com.upc.widegreenapi.service.TipoEventoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoEventoServiceImpl implements TipoEventoService {
    @Autowired
    private TipoEventoRepository tipoEventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoEventoDTO registrarTipoEvento(TipoEventoDTO dto) {
        TipoEvento tipoEvento = modelMapper.map(dto, TipoEvento.class);
        return modelMapper.map(tipoEventoRepository.save(tipoEvento), TipoEventoDTO.class);
    }

    @Override
    public List<TipoEventoDTO> listarTipoEventos() {
        return tipoEventoRepository.findAll().stream()
                .map(e -> modelMapper.map(e, TipoEventoDTO.class))
                .collect(Collectors.toList());
    }
}
