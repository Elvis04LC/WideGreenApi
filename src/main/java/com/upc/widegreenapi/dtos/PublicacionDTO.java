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
public class PublicacionDTO {
    private Long idPublicacion;
    private String titulo;
    private String contenido;
    private LocalDateTime fecha;
    private String usuarioEmail;
}
