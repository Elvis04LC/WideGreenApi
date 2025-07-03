package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.dtos.EventoOrganizadorDTO;
import com.upc.widegreenapi.entities.EventoOrganizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventoOrganizadorRepository extends JpaRepository<EventoOrganizador, Long> {
    List<EventoOrganizador> findByEvento_IdEvento(Long idEvento);
}
