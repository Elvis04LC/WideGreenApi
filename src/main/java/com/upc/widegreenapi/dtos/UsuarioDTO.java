package com.upc.widegreenapi.dtos;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long idUsuario;
    @Email
    private String email;
    private String username;
    private String password; //para ver
}
