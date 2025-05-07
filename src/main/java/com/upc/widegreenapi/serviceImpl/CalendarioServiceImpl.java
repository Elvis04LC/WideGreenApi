package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.CalendarioDTO;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.CalendarioRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.CalendarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarioServiceImpl implements CalendarioService {
    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CalendarioDTO crearCalendario(CalendarioDTO calendarioDTO) {
        Usuario usuario = usuarioRepository.findById(calendarioDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Calendario calendario = Calendario.builder()
                .usuario(usuario)
                .build();

        Calendario guardado = calendarioRepository.save(calendario);
        return modelMapper.map(guardado, CalendarioDTO.class);
    }

    @Override
    public CalendarioDTO obtenerCalendarioPorUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Calendario calendario = calendarioRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Calendario no encontrado"));

        return modelMapper.map(calendario, CalendarioDTO.class);
    }

    @Override
    public List<CalendarioDTO> listarCalendarios() {
        return calendarioRepository.findAll().stream()
                .map(calendario -> modelMapper.map(calendario, CalendarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarCalendario(Long id) {
        calendarioRepository.deleteById(id);
    }
}
