package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "mensaje_foro")
@NoArgsConstructor
@AllArgsConstructor
public class MensajeForo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensaje")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_foro", referencedColumnName = "id_foro")
    private Foro foro;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    private String contenido;

    private LocalDate fecha;
}
