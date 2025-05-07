package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "mapa_distrito")
@NoArgsConstructor
@AllArgsConstructor
public class MapaDistrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distrito")
    private Long id;

    @Column(name = "nombre_distrito")
    private String nombreDistrito;

    private String zona;

    private Double latitud;

    private Double longitud;
}
