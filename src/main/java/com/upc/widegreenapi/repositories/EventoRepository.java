package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    @Query("SELECT e FROM Evento e WHERE LOWER(e.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))")
    List<Evento> buscarEventosPorUbicacion(@Param("ubicacion") String ubicacion);

}
