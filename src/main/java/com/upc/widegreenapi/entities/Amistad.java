package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "amistad")
@NoArgsConstructor
@AllArgsConstructor
public class Amistad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_amistad")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario_1", referencedColumnName = "id_usuario")
    private Usuario usuario1;

    @ManyToOne
    @JoinColumn(name = "id_usuario_2", referencedColumnName = "id_usuario")
    private Usuario usuario2;

    private String estado;
}
