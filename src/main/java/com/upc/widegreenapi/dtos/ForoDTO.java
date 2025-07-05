package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ForoDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long idUsuario; // Se almacena solo el ID del usuario para la referencia
    private LocalDate fechaCreacion;
    private String nombreUsuario; // nombre usuario

}
