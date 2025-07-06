package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Query("SELECT EXTRACT(MONTH FROM u.fechaRegistro) AS mes, COUNT(u.idUsuario) AS cantidad " +
            "FROM Usuario u GROUP BY EXTRACT(MONTH FROM u.fechaRegistro) ORDER BY mes")
    List<Object[]> cantidadUsuariosPorMes();
}
