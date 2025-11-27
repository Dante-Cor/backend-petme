package com.petme.backend.service;

import com.petme.backend.exceptions.PublicacionNotFoundException;
import com.petme.backend.model.Mascotas;
import com.petme.backend.model.Publicacion;
import com.petme.backend.model.User;
import com.petme.backend.repository.MascotasRepository; // <--- NUEVO IMPORT
import com.petme.backend.repository.PublicacionRepository;
import com.petme.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <--- IMPORTANTE

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UserRepository userRepository;
    private final MascotasRepository mascotasRepository; // <--- NUEVO REPOSITORIO

    public PublicacionService(PublicacionRepository publicacionRepository,
                              UserRepository userRepository,
                              MascotasRepository mascotasRepository) {
        this.publicacionRepository = publicacionRepository;
        this.userRepository = userRepository;
        this.mascotasRepository = mascotasRepository;
    }

    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    @Transactional // <--- Esto asegura que todo se guarde junto o nada
    public Publicacion createPublicacion(Publicacion newPublicacion) {

        // 1. Validar Usuario Real
        Long userId = newPublicacion.getUsuario().getId();
        User usuarioReal = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        newPublicacion.setUsuario(usuarioReal);

        // 2. GUARDAR MASCOTA MANUALMENTE (Para evitar el error TransientObjectException)
        if (newPublicacion.getMascota() != null) {
            Mascotas mascota = newPublicacion.getMascota();
            mascota.setUser(usuarioReal); // Asignar dueño

            // Asignar fechas si no vienen
            if(mascota.getFecha_publicacion() == null) mascota.setFecha_publicacion(LocalDateTime.now());
            if(mascota.getFecha_actualizacion() == null) mascota.setFecha_actualizacion(LocalDateTime.now());
            if(mascota.getEstado_adopcion() == null) mascota.setEstado_adopcion("DISPONIBLE");

            // ¡AQUÍ ESTÁ LA MAGIA! Guardamos primero la mascota y obtenemos la versión persistente
            Mascotas mascotaGuardada = mascotasRepository.save(mascota);
            newPublicacion.setMascota(mascotaGuardada);
        }

        // 3. Preparar Publicación
        if (newPublicacion.getFechaPublicacion() == null) {
            newPublicacion.setFechaPublicacion(LocalDateTime.now());
        }
        if (newPublicacion.getLikes() == null) {
            newPublicacion.setLikes(0);
        }

        // 4. Guardar Publicación (Ahora la mascota ya no es "Transient", ya tiene ID)
        return publicacionRepository.save(newPublicacion);
    }

    // ... (El resto de tus métodos findById, delete, update siguen igual) ...
    public Publicacion findById(Long id) {
        return publicacionRepository.findById(id).orElseThrow(() -> new PublicacionNotFoundException(id));
    }
    public void deletePublicacion(Long id) {
        if(publicacionRepository.existsById(id)) publicacionRepository.deleteById(id);
        else throw new PublicacionNotFoundException(id);
    }
    public Publicacion updatePublicacion(Publicacion p, Long id) {
        return publicacionRepository.findById(id).map(e -> {
            e.setTitulo(p.getTitulo()); e.setTipo(p.getTipo()); e.setLikes(p.getLikes());
            return publicacionRepository.save(e);
        }).orElseThrow(() -> new PublicacionNotFoundException(id));
    }
}