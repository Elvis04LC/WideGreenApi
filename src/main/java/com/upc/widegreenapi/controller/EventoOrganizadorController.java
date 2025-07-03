package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.EventoOrganizadorDTO;
import com.upc.widegreenapi.service.EventoOrganizadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evento-organizador")
@CrossOrigin
public class EventoOrganizadorController {
    @Autowired
    private EventoOrganizadorService servicio;

    @PostMapping("/registrar")
    public ResponseEntity<EventoOrganizadorDTO> registrar(@RequestBody EventoOrganizadorDTO dto) {
        return ResponseEntity.ok(servicio.registrar(dto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EventoOrganizadorDTO>> listar() {
        return ResponseEntity.ok(servicio.listar());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-evento/{idEvento}")
    public ResponseEntity<List<EventoOrganizadorDTO>> listarPorEvento(@PathVariable Long idEvento) {
        return ResponseEntity.ok(servicio.listarPorEvento(idEvento));
    }
}
