package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.CategoriaPublicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaPublicacionRepository extends JpaRepository<CategoriaPublicacion, Long> {
    Optional<CategoriaPublicacion> findByNombreCategoria(String nombreCategoria);
}
