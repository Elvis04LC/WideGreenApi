package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "noticia")
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_noticia")
    private Long id;

    private String titulo;

    private String contenido;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
    private MapaDistrito distrito;

}
