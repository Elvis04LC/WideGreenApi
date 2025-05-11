package com.upc.widegreenapi.service;

import com.upc.widegreenapi.dtos.MensajeDTO;
import com.upc.widegreenapi.dtos.OrganizadorEventoDTO;

import java.util.List;

public interface OrganizadorEventoService {
    OrganizadorEventoDTO registrarOrganizador(OrganizadorEventoDTO dto);
    List<OrganizadorEventoDTO> listarOrganizadores();
    String eliminarOrganizador(Long id);
}
