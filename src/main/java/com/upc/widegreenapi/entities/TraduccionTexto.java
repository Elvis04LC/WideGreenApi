package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "traduccion_texto")
@NoArgsConstructor
@AllArgsConstructor
public class TraduccionTexto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_traduccion")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_idioma", referencedColumnName = "id_idioma")
    private IdiomaSoportado idioma;

    @Column(name = "clave_texto")
    private String claveTexto;

    private String traduccion;
}
