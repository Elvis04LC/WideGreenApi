package com.upc.widegreenapi.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaDTO {
    private Long id;
    private String titulo;
    private String contenido;
    private LocalDate fecha;
}
