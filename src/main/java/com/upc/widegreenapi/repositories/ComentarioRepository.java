package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Comentario;
import com.upc.widegreenapi.entities.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacion(Publicacion publicacion);
}
