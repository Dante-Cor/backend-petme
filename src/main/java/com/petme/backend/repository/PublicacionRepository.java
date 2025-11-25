package com.petme.backend.repository;

import com.petme.backend.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    // Add custom queries here if needed later
    // Permite filtrar "Mis Publicaciones"

    List<Publicacion> findAllByUser_Id(Long userId);
}