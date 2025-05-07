package com.upc.widegreenapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilUsuarioDTO {
    private Long id;
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String foto;
    private String bio;
}
