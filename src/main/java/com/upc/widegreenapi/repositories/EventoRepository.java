package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findByUbicacion(String ubicacion);
}
