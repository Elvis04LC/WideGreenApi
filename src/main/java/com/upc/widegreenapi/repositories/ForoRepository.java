package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Foro;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForoRepository extends JpaRepository<Foro, Long> {
    List<Foro> findByUsuario_IdUsuario(Long usuarioId);

}
