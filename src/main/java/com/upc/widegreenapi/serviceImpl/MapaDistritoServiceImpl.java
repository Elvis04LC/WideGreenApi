package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.MapaDistritoDTO;
import com.upc.widegreenapi.repositories.MapaDistritoRepository;
import com.upc.widegreenapi.service.MapaDistritoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapaDistritoServiceImpl implements MapaDistritoService {
    @Autowired
    private MapaDistritoRepository mapaDistritoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MapaDistritoDTO> listarDistritos() {
        return mapaDistritoRepository.findAll()
                .stream()
                .map(distrito -> modelMapper.map(distrito, MapaDistritoDTO.class))
                .toList();
    }
}
