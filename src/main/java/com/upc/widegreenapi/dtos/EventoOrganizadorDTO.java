package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoOrganizadorDTO {
    private Long id;
    private Long idEvento;
    private Long idOrganizador;
}
