package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "multimedia_publicacion")
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaPublicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_multimedia")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_publicacion", referencedColumnName = "id_publicacion")
    private Publicacion publicacion;

    private String url;

    private String tipo;
}
