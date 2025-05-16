package com.upc.widegreenapi.repositories;


import com.upc.widegreenapi.entities.PublicacionCategoria;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicacionCategoriaRepository extends JpaRepository <PublicacionCategoria, Long>{
    List<PublicacionCategoria> findByPublicacion_IdPublicacion(Long idPublicacion);

    List<PublicacionCategoria> findByCategoria_Id(Long idCategoria);
    @Modifying
    @Transactional
    @Query("DELETE FROM PublicacionCategoria pc WHERE pc.publicacion.idPublicacion = :idPublicacion")
    void deleteByPublicacionId(@Param("idPublicacion") Long idPublicacion);
}
