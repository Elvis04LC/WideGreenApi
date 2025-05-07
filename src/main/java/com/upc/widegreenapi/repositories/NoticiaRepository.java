package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    List<Noticia> findByFecha(LocalDate fecha);
    List<Noticia> findByTituloContainingIgnoreCase(String titulo);
}
