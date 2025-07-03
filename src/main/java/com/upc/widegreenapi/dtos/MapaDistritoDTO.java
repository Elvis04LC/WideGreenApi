package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapaDistritoDTO {
    private Long id;
    private String nombreDistrito;
    private String zona;
    private Double latitud;
    private Double longitud;
}
