package com.upc.wisegreenapi.repositories;

import com.upc.wisegreenapi.entities.Publicacion;
import com.upc.wisegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByUsuario(Usuario usuario);
}
