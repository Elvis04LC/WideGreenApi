package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.PerfilUsuarioDTO;
import com.upc.widegreenapi.dtos.PublicacionDTO;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.PublicacionRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.PublicacionService;
import com.upc.widegreenapi.serviceImpl.AlmacenamientoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin
public class PublicacionController {
    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private AlmacenamientoService almacenamientoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private ModelMapper modelMapper;



    @PostMapping("/crear")
    public ResponseEntity<PublicacionDTO> crearPublicacion(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile,
            @RequestParam(value = "urlImagen", required = false) String urlImagen) {

        try {
            // Obtener el usuario autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName(); // Obtenemos el email del usuario autenticado
            System.out.println("Usuario autenticado: " + email);


            // Buscar el usuario por email
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Crear la URL de la imagen (si existe)
            String imagenUrl = null;
            if (imagenFile != null && !imagenFile.isEmpty()) {
                imagenUrl = almacenamientoService.guardarImagen(imagenFile);
            } else if (urlImagen != null && !urlImagen.isEmpty()) {
                imagenUrl = urlImagen;
            }

            // Crear la publicación y asociar el usuario
            Publicacion publicacion = new Publicacion();
            publicacion.setTitulo(titulo);
            publicacion.setContenido(contenido);
            publicacion.setImagenUrl(imagenUrl);
            publicacion.setFecha(LocalDateTime.now());
            publicacion.setUsuario(usuario);  // Asegúrate de que el usuario no sea nulo

            // Guardar la publicación en la base de datos
            Publicacion guardada = publicacionRepository.save(publicacion);

            // Convertir la publicación a DTO y devolver la respuesta
            PublicacionDTO dto = modelMapper.map(guardada, PublicacionDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<PublicacionDTO>> obtenerPublicacionesPorCategoria(@PathVariable Long idCategoria) {
        // Llamar al servicio para obtener las publicaciones filtradas por categoría
        List<PublicacionDTO> publicaciones = publicacionService.obtenerPublicacionesPorCategoria(idCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(publicaciones);
    }

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> listarPublicaciones() {
        List<PublicacionDTO> publicaciones = publicacionService.listarPublicaciones();
        return ResponseEntity.ok(publicaciones);
    }
    @PutMapping("/editar/{idPublicacion}")
    public ResponseEntity<PublicacionDTO> editarPublicacion(@PathVariable Long idPublicacion, @RequestParam String nuevoContenido) {
        System.out.println("ID de Publicación: " + idPublicacion);
        System.out.println("Nuevo Contenido: " + nuevoContenido);
        PublicacionDTO actualizada = publicacionService.editarPublicacion(idPublicacion, nuevoContenido);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/eliminar/{idPublicacion}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long idPublicacion) {
        publicacionService.eliminarPublicacion(idPublicacion);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<PublicacionDTO>> listarPorUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<PublicacionDTO> publicaciones = publicacionService.listarPorUsuario(usuario.getIdUsuario());
        return ResponseEntity.ok(publicaciones);
    }


}
