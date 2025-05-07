package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "actividad_calendario")
@NoArgsConstructor
@AllArgsConstructor
public class ActividadCalendario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_calendario", referencedColumnName = "id_calendario")
    private Calendario calendario;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = true)
    private Evento evento;


    private String titulo;

    private LocalDate fecha;

    private LocalTime hora;

    private String descripcion;
}
