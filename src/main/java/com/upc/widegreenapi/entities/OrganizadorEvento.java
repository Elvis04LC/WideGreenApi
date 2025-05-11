package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizador_evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizadorEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organizador")
    private Long idOrganizador;

    @NotNull
    @Column(name = "nombre_organizador")
    @Size(max = 100, message = "El nombre del organizador no debe exceder los 100 caracteres")
    private String nombreOrganizador;

    private String contacto;
}

