package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_evento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoEvento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_evento")
    private Long idTipoEvento;

    @Column(name = "nombre_tipo")
    private String nombreTipo;
}
