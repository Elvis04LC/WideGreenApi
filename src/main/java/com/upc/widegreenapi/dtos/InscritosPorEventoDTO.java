package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscritosPorEventoDTO {
    private String nombre;
    private Long totalI;
}
