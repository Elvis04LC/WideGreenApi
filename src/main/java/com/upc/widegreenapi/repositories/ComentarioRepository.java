package com.upc.wisegreenapi.repositories;

import com.upc.wisegreenapi.entities.Comentario;
import com.upc.wisegreenapi.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacion(Publicacion publicacion);
}
