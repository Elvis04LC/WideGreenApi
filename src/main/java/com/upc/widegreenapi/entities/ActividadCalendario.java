package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_evento",  referencedColumnName = "id_evento", nullable = false)
    private Evento evento;

    @NotNull
    private String titulo;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private LocalTime hora;

    @Size(max = 500)
    private String descripcion;
}
