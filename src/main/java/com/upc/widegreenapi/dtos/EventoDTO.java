package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoDTO {
    private Long idEvento;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDateTime fecha;
    private Long idTipoEvento;

}
