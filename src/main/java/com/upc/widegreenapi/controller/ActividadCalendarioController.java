package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.ActividadCalendarioDTO;
import com.upc.widegreenapi.service.ActividadCalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actividades-calendario")
@CrossOrigin(origins = "http://localhost:4200")
public class ActividadCalendarioController {
    @Autowired
    private ActividadCalendarioService actividadCalendarioService;

    @PostMapping("/crear")
    public ResponseEntity<ActividadCalendarioDTO> registrarActividad(@RequestBody ActividadCalendarioDTO dto) {
        ActividadCalendarioDTO creada = actividadCalendarioService.registrarActividad(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/listar/{idCalendario}")
    public ResponseEntity<List<ActividadCalendarioDTO>> listarActividadesPorCalendario(@PathVariable Long idCalendario) {
        List<ActividadCalendarioDTO> actividades = actividadCalendarioService.listarActividadesPorCalendario(idCalendario);
        return ResponseEntity.ok(actividades);
    }

    @GetMapping("/{idActividad}")
    public ResponseEntity<ActividadCalendarioDTO> obtenerActividad(@PathVariable Long idActividad) {
        ActividadCalendarioDTO actividad = actividadCalendarioService.obtenerActividad(idActividad);
        return ResponseEntity.ok(actividad);
    }

    @DeleteMapping("/{idActividad}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long idActividad) {
        actividadCalendarioService.eliminarActividad(idActividad);
        return ResponseEntity.noContent().build();
    }
}
