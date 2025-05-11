package com.upc.widegreenapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "publicacion_categoria")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacionCategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Se puede agregar un ID técnico para facilitar el manejo en JPA

    @ManyToOne
    @JoinColumn(name = "id_publicacion", referencedColumnName = "id_publicacion")
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    private CategoriaPublicacion categoria;
}
