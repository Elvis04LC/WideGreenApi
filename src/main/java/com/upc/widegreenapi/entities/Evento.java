package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;

    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;
    private String ubicacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evento")
    private TipoEvento tipoEvento;

}
