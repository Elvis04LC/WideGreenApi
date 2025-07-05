package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private Long id;
    private Long idUsuario;
    private String contenido;
    private LocalDateTime fecha;
    private Boolean visto;
    private Long idPublicacion;           // Puede ser null
    private String tituloPublicacion;
}
