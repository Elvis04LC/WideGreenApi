package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "estadistica_distrital")
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaDistrital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estadistica")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_distrito", referencedColumnName = "id_distrito")
    private MapaDistrito distrito;

    private String descripcion;

    private Double valor;
}
