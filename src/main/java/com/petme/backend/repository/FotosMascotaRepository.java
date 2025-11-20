package com.petme.backend.repository;

import com.petme.backend.model.FotosMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotosMascotaRepository extends JpaRepository <FotosMascota, Long> {
}
