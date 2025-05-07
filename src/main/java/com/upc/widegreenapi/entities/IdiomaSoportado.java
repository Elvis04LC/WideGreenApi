package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "idioma_soportado")
@NoArgsConstructor
@AllArgsConstructor
public class IdiomaSoportado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_idioma")
    private Long id;

    @Column(name = "nombre_idioma")
    private String nombreIdioma;

    @Column(name = "codigo_iso")
    private String codigoIso;
}
