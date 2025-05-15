package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Comentario;
import com.upc.widegreenapi.entities.Publicacion;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacion(Publicacion publicacion);
    List<Comentario> findByUsuario(Usuario usuario);
    Optional<Comentario> findByIdComentarioAndUsuario(Long idComentario, Usuario usuario);

    @Modifying
    @Query("DELETE FROM Comentario c WHERE c.publicacion.idPublicacion = :idPublicacion")
    void deleteByPublicacionId(@Param("idPublicacion") Long idPublicacion);
}
