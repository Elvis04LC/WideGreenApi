package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.NotificacionDTO;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.NotificacionService;
import com.upc.widegreenapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener notificaciones del usuario autenticado
    @GetMapping("/mis-notificaciones")
    public List<NotificacionDTO> obtenerMisNotificaciones() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return notificacionService.obtenerPorUsuario(usuario.getIdUsuario());
    }

    // ✅ Marcar una notificación como vista
    @PostMapping("/marcar-visto/{id}")
    public ResponseEntity<String> marcarComoVisto(@PathVariable Long id) {
        notificacionService.marcarComoVisto(id);
        return ResponseEntity.ok().build();
    }
}
