package com.petme.backend.repository;

import com.petme.backend.model.Mascotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotasRepository extends JpaRepository<Mascotas, Long> {

    List<Mascotas> findAllByUser_Id(Long userId);
}
