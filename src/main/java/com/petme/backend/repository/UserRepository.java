package com.petme.backend.repository;

import com.petme.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

        // Buscar por username
        User findByUsername(String username);

        // Buscar por email
        User findByEmail(String email);

        // Buscar por país
        List<User> findByCountry(String country);

        // Buscar por ciudad
        List<User> findByCity(String city);

        // Buscar por teléfono
        User findByTelephone(Long telephone);

}
