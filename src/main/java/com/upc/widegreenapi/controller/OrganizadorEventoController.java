package com.upc.widegreenapi.controller;

import com.upc.widegreenapi.dtos.MensajeDTO;
import com.upc.widegreenapi.dtos.OrganizadorEventoDTO;
import com.upc.widegreenapi.service.OrganizadorEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizadores")
@CrossOrigin
public class OrganizadorEventoController {
    @Autowired
    private OrganizadorEventoService organizadorService;

    @PostMapping("/registrar")
    public OrganizadorEventoDTO registrar(@RequestBody OrganizadorEventoDTO dto) {
        return organizadorService.registrarOrganizador(dto);
    }

    @GetMapping
    public List<OrganizadorEventoDTO> listar() {
        return organizadorService.listarOrganizadores();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeDTO> eliminarOrganizador(@PathVariable Long id) {
        MensajeDTO mensajeDTO = new MensajeDTO();
        mensajeDTO.setMensaje(organizadorService.eliminarOrganizador(id));
        return ResponseEntity.ok(mensajeDTO);
    }


}
