package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.PublicacionCategoriaDTO;
import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.CategoriaPublicacion;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.PublicacionCategoria;
import com.upc.widegreenapi.repositories.CategoriaPublicacionRepository;
import com.upc.widegreenapi.repositories.PublicacionCategoriaRepository;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.service.PublicacionCategoriaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionCategoriaServiceImpl implements PublicacionCategoriaService {
    @Autowired
    private PublicacionCategoriaRepository publicacionCategoriaRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private CategoriaPublicacionRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PublicacionCategoriaDTO asociarCategoriaAPublicacion(PublicacionCategoriaDTO dto) {
        Publicacion publicacion = publicacionRepository.findById(dto.getIdPublicacion())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        CategoriaPublicacion categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        PublicacionCategoria publicacionCategoria = PublicacionCategoria.builder()
                .publicacion(publicacion)
                .categoria(categoria)
                .build();

        PublicacionCategoria guardada = publicacionCategoriaRepository.save(publicacionCategoria);

        return modelMapper.map(guardada, PublicacionCategoriaDTO.class);
    }

    @Override
    public List<PublicacionCategoriaDTO> listarCategoriasPorPublicacion(Long idPublicacion) {
        List<PublicacionCategoria> lista = publicacionCategoriaRepository.findByPublicacion_IdPublicacion(idPublicacion);
        return lista.stream()
                .map(pc -> modelMapper.map(pc, PublicacionCategoriaDTO.class))
                .collect(Collectors.toList());
    }



}
