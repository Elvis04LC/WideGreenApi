package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.CategoriaPublicacionDTO;
import com.upc.widegreenapi.entities.CategoriaPublicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.CategoriaPublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.CategoriaPublicacionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaPublicacionServiceImpl implements CategoriaPublicacionService {
    @Autowired
    private CategoriaPublicacionRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoriaPublicacionDTO crearCategoria(CategoriaPublicacionDTO categoriaDTO) {
        CategoriaPublicacion categoria = modelMapper.map(categoriaDTO, CategoriaPublicacion.class);
        CategoriaPublicacion guardada = categoriaRepository.save(categoria);
        return modelMapper.map(guardada, CategoriaPublicacionDTO.class);
    }

    @Override
    public List<CategoriaPublicacionDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaPublicacionDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public CategoriaPublicacionDTO editarCategoria(CategoriaPublicacionDTO categoriaDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el usuario es ADMIN
        if (!usuario.getRole().equals("ADMIN")) {
            throw new RuntimeException("No tienes permiso para editar esta categoría");
        }

        // Buscar la categoría por ID
        CategoriaPublicacion categoria = categoriaRepository.findById(categoriaDTO.getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaDTO.getId()));

        // Validar el nombre de la categoría
        if (categoriaDTO.getNombreCategoria() == null || categoriaDTO.getNombreCategoria().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede estar vacío");
        }

        // Actualizar la categoría
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());

        CategoriaPublicacion actualizada = categoriaRepository.save(categoria);

        return modelMapper.map(actualizada, CategoriaPublicacionDTO.class);
    }
    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
