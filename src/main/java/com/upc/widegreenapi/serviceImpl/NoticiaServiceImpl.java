package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.NoticiaDTO;
import com.upc.widegreenapi.entities.Noticia;
import com.upc.widegreenapi.repositories.NoticiaRepository;
import com.upc.widegreenapi.service.NoticiaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticiaServiceImpl implements NoticiaService {
    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NoticiaDTO crearNoticia(NoticiaDTO noticiaDTO) {
        Noticia noticia = modelMapper.map(noticiaDTO, Noticia.class);
        Noticia guardada = noticiaRepository.save(noticia);
        return modelMapper.map(guardada, NoticiaDTO.class);
    }

    @Override
    public NoticiaDTO obtenerNoticiaPorId(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));
        return modelMapper.map(noticia, NoticiaDTO.class);
    }

    @Override
    public List<NoticiaDTO> listarNoticias() {
        return noticiaRepository.findAll()
                .stream()
                .map(noticia -> modelMapper.map(noticia, NoticiaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticiaDTO> filtrarNoticiasPorFecha(LocalDate fecha) {
        return noticiaRepository.findByFecha(fecha)
                .stream()
                .map(noticia -> modelMapper.map(noticia, NoticiaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticiaDTO> filtrarNoticiasPorTema(String tema) {
        return noticiaRepository.findByTituloContainingIgnoreCase(tema)
                .stream()
                .map(noticia -> modelMapper.map(noticia, NoticiaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public NoticiaDTO actualizarNoticia(Long id, NoticiaDTO noticiaDTO) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticia.setTitulo(noticiaDTO.getTitulo());
        noticia.setContenido(noticiaDTO.getContenido());
        noticia.setFecha(noticiaDTO.getFecha());

        Noticia actualizada = noticiaRepository.save(noticia);
        return modelMapper.map(actualizada, NoticiaDTO.class);
    }

    @Override
    public void eliminarNoticia(Long id) {
        noticiaRepository.deleteById(id);
    }
}
