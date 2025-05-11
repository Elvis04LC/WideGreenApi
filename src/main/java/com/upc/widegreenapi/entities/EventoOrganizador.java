package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evento_organizador",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id_evento", "id_organizador"})
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoOrganizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // clave artificial para facilitar JPA

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_organizador")
    private OrganizadorEvento organizador;
}
