package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.ActividadCalendario;
import com.upc.widegreenapi.entities.Calendario;
import com.upc.widegreenapi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ActividadCalendarioRepository extends JpaRepository<ActividadCalendario, Long> {
    List<ActividadCalendario> findByCalendarioId(Long idCalendario);
    Optional<ActividadCalendario> findByIdAndCalendarioId(Long id, Long idCalendario);
    boolean existsByCalendarioIdAndFechaAndHora(Long idCalendario, LocalDate fecha, LocalTime hora);
    boolean existsByCalendarioAndEvento(Calendario calendario, Evento evento);


}
