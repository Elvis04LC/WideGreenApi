package com.upc.wisegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilUsuarioDTO {
    private String nombre;
    private String apellido;
    private String foto;
    private String bio;
}
