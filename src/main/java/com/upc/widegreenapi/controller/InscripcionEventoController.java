package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.InscripcionEventoDTO;
import com.upc.widegreenapi.dtos.InscritosPorEventoDTO;
import com.upc.widegreenapi.service.InscripcionEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin
public class InscripcionEventoController {
    @Autowired
    private InscripcionEventoService inscripcionEventoService;
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

}
