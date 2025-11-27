package com.petme.backend.repository;

import com.petme.backend.model.Mascotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotasRepository extends JpaRepository<Mascotas, Long> {

    List<Mascotas> findAllByUser_Id(Long userId);

    @Query("SELECT m FROM Mascotas m " +
            "WHERE (:especie IS NULL OR m.especie = :especie) " +
            "AND (:tamano IS NULL OR m.tamaño = :tamano) " +
            "AND (:edad IS NULL OR m.edad <= :edad) " +
            "ORDER BY m.fecha_publicacion DESC")
    List<Mascotas> findMascotasByFiltros(
            @Param("especie") String especie,
            @Param("tamano") String tamano,
            @Param("edad") Integer edad
    );
}
