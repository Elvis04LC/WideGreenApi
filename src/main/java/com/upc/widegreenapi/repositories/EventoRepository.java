package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
