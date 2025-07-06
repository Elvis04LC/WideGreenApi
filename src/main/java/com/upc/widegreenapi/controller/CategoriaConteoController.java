package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.CategoriaConteoDTO;
import com.upc.widegreenapi.service.CategoriaConteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categoria-conteo")
public class CategoriaConteoController {
    @Autowired
    private CategoriaConteoService categoriaConteoService;

    @GetMapping("/cantidadPorCategoria")
    public ResponseEntity<List<CategoriaConteoDTO>> cantidadPorCategoria() {
        return ResponseEntity.ok(categoriaConteoService.cantidadPublicacionesPorCategoria());
    }
}
