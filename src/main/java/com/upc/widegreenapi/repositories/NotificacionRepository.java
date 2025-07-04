package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long>{
    List<Notificacion> findByUsuario_IdUsuarioOrderByFechaDesc(Long idUsuario);
}
