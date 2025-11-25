package com.petme.backend.repository;

import com.petme.backend.model.Adopcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Long> {

    List<Adopcion> findByEstado(String estado);

    // Permite ver "Mis Solicitudes"
    List<Adopcion> findAllByUser_Id(Long userId);

}