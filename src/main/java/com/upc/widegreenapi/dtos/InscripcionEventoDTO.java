package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class InscripcionEventoDTO {
    private Long idUsuario;       // Identificador del usuario inscrito
    private Long idEvento;        // Identificador del evento
    private LocalDateTime fechaInscripcion;
}
