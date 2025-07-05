package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.InscripcionEventoDTO;
import com.upc.widegreenapi.dtos.InscritosPorEventoDTO;
import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.Usuario;
import com.upc.widegreenapi.exceptions.EventNotFoundException;
import com.upc.widegreenapi.repositories.EventoRepository;
import com.upc.widegreenapi.repositories.InscripcionEventoRepository;
import com.upc.widegreenapi.repositories.UsuarioRepository;
import com.upc.widegreenapi.service.InscripcionEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin
public class InscripcionEventoController {
    @Autowired
    private InscripcionEventoService inscripcionEventoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private InscripcionEventoRepository inscripcionEventoRepository;

    @Autowired
    private EventoRepository eventoRepository;
    //Registrar inscripción a un evento
    @PostMapping("/registrar")
    public InscripcionEventoDTO registrarInscripcion(@RequestBody InscripcionEventoDTO inscripcionEventoDTO) {
        return inscripcionEventoService.registrarInscripcion(inscripcionEventoDTO);
    }

    //Cancelar inscripción
    @DeleteMapping("/cancelar/{id}")
    public void cancelarInscripcion(@PathVariable Long id) {
        inscripcionEventoService.cancelarInscripcion(id);
    }

    //Listar inscripciones por usuario
    @GetMapping("/usuario/{idUsuario}")
    public List<InscripcionEventoDTO> listarInscripcionesPorUsuario(@PathVariable Long idUsuario) {
        return inscripcionEventoService.listarInscripcionesPorUsuario(idUsuario);
    }

    //Ver el numero de inscritos por la cantidad de eventos realizados
    @GetMapping("/CantidadPorEvento")
    public ResponseEntity<List<InscritosPorEventoDTO>> mostrarCantidadInscripcionesPorEvento() {
        return ResponseEntity.ok(inscripcionEventoService.mostrarCantidadInscripcionesPorEvento());
    }
    @GetMapping("/verificar/{idEvento}")
    public ResponseEntity<Boolean> verificarInscripcion(@PathVariable Long idEvento) {
        // Obtener el email del usuario autenticado
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener el evento con el idEvento
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new EventNotFoundException("Evento no encontrado con ID: " + idEvento));

        // Verificar si el usuario ya está inscrito en este evento
        boolean isRegistered = inscripcionEventoRepository.existsByUsuarioAndEvento(usuario, evento);

        return ResponseEntity.ok(isRegistered);
    }
}
