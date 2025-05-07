package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    Optional<Calendario> findByUsuario(Usuario usuario);
}
