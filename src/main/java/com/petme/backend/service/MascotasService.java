package com.petme.backend.service;

import com.petme.backend.model.Mascotas;
import com.petme.backend.model.User;
import com.petme.backend.repository.MascotasRepository;
import com.petme.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MascotasService {
    private final MascotasRepository mascotasRepository;
    private final UserRepository userRepository;

    public MascotasService(MascotasRepository mascotasRepository, UserRepository userRepository){
        this.mascotasRepository = mascotasRepository;
        this.userRepository = userRepository;
    }

    // 1. Traer todas
    public List<Mascotas> getAllMascotas(){
        return mascotasRepository.findAll();
    }

    // --- MÉTODO NUEVO: CREAR MASCOTA ---
    public Mascotas createMascota(Mascotas mascota, Long userId) {
        // 1. Buscar al usuario (Dueño)
        User dueño = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // 2. Asignar el usuario a la mascota
        mascota.setUser(dueño);

        // 3. Asignar fechas automáticas (No confiar en el frontend)
        mascota.setFecha_publicacion(LocalDateTime.now());
        mascota.setFecha_actualizacion(LocalDateTime.now());

        // 4. Guardar
        return mascotasRepository.save(mascota);

        }
    // --- 3. MÉTODO FALTANTE: Buscar por Usuario ---
    public List<Mascotas> getByUserId(Long userId) {
        // Este método llama al que creamos en el Repositorio
        return mascotasRepository.findAllByUser_Id(userId);

    }
    public Mascotas findById(Long id) {
        return mascotasRepository.findById(id).orElse(null);
    }

    // Método para actualizar una mascota existente
    public Mascotas actualizarMascota(Mascotas mascota) {
        mascota.setFecha_actualizacion(LocalDateTime.now());
        return mascotasRepository.save(mascota);
    }
}
