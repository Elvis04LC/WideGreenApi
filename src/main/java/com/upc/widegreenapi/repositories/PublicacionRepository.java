package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    @Query("SELECT p FROM Publicacion p WHERE p.usuario.idUsuario = :idUsuario")
    List<Publicacion> findByUsuarioId(@Param("idUsuario") Long idUsuario);
}
