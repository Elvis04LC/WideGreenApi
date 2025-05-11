package com.upc.widegreenapi.repositories;

import com.upc.widegreenapi.entities.OrganizadorEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizadorEventoRepository extends JpaRepository<OrganizadorEvento, Long> {
    Optional<OrganizadorEvento> findByNombreOrganizador(String nombreOrganizador);
    Optional<OrganizadorEvento> findByContacto(String contacto);
    boolean existsByNombreOrganizadorAndContacto(String nombreOrganizador, String contacto);

}
