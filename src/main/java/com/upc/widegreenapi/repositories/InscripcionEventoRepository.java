package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Evento;
import com.upc.widegreenapi.entities.InscripcionEvento;
import com.upc.widegreenapi.entities.Usuario;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InscripcionEventoRepository extends JpaRepository<InscripcionEvento, Long> {
    boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);
    List<InscripcionEvento> findByUsuario(Usuario usuario);
    Optional<InscripcionEvento> findByUsuarioAndEvento(Usuario usuario, Evento evento);
    @Query("SELECT e.nombre AS nombre_evento, COUNT(ie) AS num_inscritos " +
            "FROM InscripcionEvento ie " +
            "JOIN ie.evento e " +
            "GROUP BY e.nombre ")
    List<Tuple> obtenerTotalInscritosSegunEvento();
}
