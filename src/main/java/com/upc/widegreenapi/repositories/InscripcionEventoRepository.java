package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.InscripcionEvento;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionEventoRepository extends JpaRepository<InscripcionEvento, Long> {
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);
    List<InscripcionEvento> findByUsuario(Usuario usuario);
    Optional<InscripcionEvento> findByUsuarioAndEvento(Usuario usuario, Evento evento);
}
