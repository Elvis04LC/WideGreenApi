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
@CrossOrigin
public class CalendarioController {
    @Autowired
    private CalendarioService calendarioService;

    @PostMapping("/crear")
    public ResponseEntity<CalendarioDTO> crearCalendario() {
        CalendarioDTO creado = calendarioService.crearCalendario();
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<CalendarioDTO> obtenerCalendarioPorUsuario(@PathVariable Long idUsuario) {
        CalendarioDTO calendario = calendarioService.obtenerCalendarioPorUsuario(idUsuario);
        return ResponseEntity.ok(calendario);
    }

    @GetMapping
    public ResponseEntity<List<CalendarioDTO>> listarCalendarios() {
        List<CalendarioDTO> calendarios = calendarioService.listarCalendarios();
        return ResponseEntity.ok(calendarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCalendario(@PathVariable Long id) {
        calendarioService.eliminarCalendario(id);
        return ResponseEntity.ok("Calendario eliminado correctamente.");
    }
    @GetMapping("/autenticado")
    public ResponseEntity<CalendarioDTO> obtenerCalendarioAutenticado() {
        CalendarioDTO calendario = calendarioService.obtenerCalendarioDelUsuarioAutenticado();
        return ResponseEntity.ok(calendario);
    }

}
