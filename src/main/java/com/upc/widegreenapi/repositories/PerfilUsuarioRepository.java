package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.PerfilUsuario;
import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {
    boolean existsByUsuario(Usuario usuario);

    Optional<PerfilUsuario> findByUsuario(Usuario usuario);

}
