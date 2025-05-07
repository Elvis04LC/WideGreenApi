package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
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

    @Column(name = "nombre_organizador")
    private String nombreOrganizador;

    private String contacto;
}

