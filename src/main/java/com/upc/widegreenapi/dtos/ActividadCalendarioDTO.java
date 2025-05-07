package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActividadCalendarioDTO {
    private Long id;
    private Long idCalendario;
    private Long idEvento;
    private LocalDate fechaEvento;
    private String titulo;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
}
