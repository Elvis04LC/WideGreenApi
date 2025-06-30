package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TipoEventoRepository extends JpaRepository<TipoEvento, Long> {
}
