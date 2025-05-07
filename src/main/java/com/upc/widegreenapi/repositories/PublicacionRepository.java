package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByUsuario(Usuario usuario);
}
