package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.CalendarioDTO;
import com.upc.widegreenapi.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {
    @Autowired
    private CalendarioService calendarioService;

    /**
     * Crear un calendario para un usuario específico.
     */
    @PostMapping("/crear")
    public ResponseEntity<CalendarioDTO> crearCalendario(@RequestBody CalendarioDTO calendarioDTO) {
        CalendarioDTO creado = calendarioService.crearCalendario(calendarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * Obtener el calendario de un usuario específico.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CalendarioDTO> obtenerCalendarioPorUsuario(@PathVariable Long idUsuario) {
        CalendarioDTO calendario = calendarioService.obtenerCalendarioPorUsuario(idUsuario);
        return ResponseEntity.ok(calendario);
    }

    /**
     * Listar todos los calendarios.
     */
    @GetMapping
    public ResponseEntity<List<CalendarioDTO>> listarCalendarios() {
        List<CalendarioDTO> calendarios = calendarioService.listarCalendarios();
        return ResponseEntity.ok(calendarios);
    }

    /**
     * Eliminar un calendario por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCalendario(@PathVariable Long id) {
        calendarioService.eliminarCalendario(id);
        return ResponseEntity.ok("Calendario eliminado correctamente.");
    }
}
