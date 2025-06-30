package com.upc.widegreenapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacionCategoriaDTO {
    @JsonIgnore
    private Long id;
    private Long idPublicacion;
    private Long idCategoria;
}
