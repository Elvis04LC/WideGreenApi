package com.upc.widegreenapi.repositories;


import com.upc.widegreenapi.entities.PublicacionCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacionCategoriaRepository extends JpaRepository <PublicacionCategoria, Long>{
    List<PublicacionCategoria> findByPublicacion_IdPublicacion(Long idPublicacion);

    List<PublicacionCategoria> findByCategoria_Id(Long idCategoria);
}
