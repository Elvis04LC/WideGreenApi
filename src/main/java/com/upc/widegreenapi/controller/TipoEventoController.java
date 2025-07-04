package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.TipoEventoDTO;
import com.upc.widegreenapi.service.TipoEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/tipo-evento")
public class TipoEventoController {
    @Autowired
    private TipoEventoService tipoEventoService;

    @PostMapping("/registrar")
    public TipoEventoDTO registrar(@RequestBody TipoEventoDTO dto) {
        return tipoEventoService.registrarTipoEvento(dto);
    }

    @GetMapping
    public List<TipoEventoDTO> listar() {
        return tipoEventoService.listarTipoEventos();
    }
}
