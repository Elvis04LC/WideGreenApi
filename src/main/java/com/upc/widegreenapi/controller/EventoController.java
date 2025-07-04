package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.EventoDTO;
import com.upc.widegreenapi.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin
public class EventoController {
    @Autowired
    private EventoService eventoService;
    //Registrar un evento
    @PostMapping("/registrar")
    public EventoDTO registrar(@RequestBody EventoDTO dto) {
        return eventoService.crearEvento(dto);
    }

    //Listar todos los eventos
    @GetMapping
    public List<EventoDTO> listar() {
        return eventoService.listarEventos();
    }

    //Obtener evento por ID
    @GetMapping("/id/{id}")
    public EventoDTO obtenerPorId(@PathVariable Long id) {
        return eventoService.obtenerEventoPorId(id);
    }

    //Actualizar un evento
    @PutMapping("/{id}")
    public EventoDTO actualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        return eventoService.actualizarEvento(id, dto);
    }

    //Eliminar un evento
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
    }

    //Obtener evento por ubicacion especifica
    @GetMapping("/ubicacion/{ubicacion}")
    public List<EventoDTO> obtenerEventoPorUbicacion(@PathVariable String ubicacion) {
        return eventoService.obtenerEventoPorUbicacion(ubicacion);
    }
}
