package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.MapaDistritoDTO;
import com.upc.widegreenapi.service.MapaDistritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mapa-distritos")
@CrossOrigin
public class MapaDistritoController {

    @Autowired
    private MapaDistritoService mapaDistritoService;

    @GetMapping
    public ResponseEntity<List<MapaDistritoDTO>> listarDistritos() {
        List<MapaDistritoDTO> distritos = mapaDistritoService.listarDistritos();
        return ResponseEntity.ok(distritos);
    }
}
