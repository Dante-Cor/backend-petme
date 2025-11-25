package com.petme.backend.service;

import com.petme.backend.exceptions.PublicacionNotFoundException;
import com.petme.backend.model.Publicacion;
import com.petme.backend.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;

    public PublicacionService(PublicacionRepository publicacionRepository) {
        this.publicacionRepository = publicacionRepository;
    }

    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

   /*public Publicacion createPublicacion(Publicacion newPublicacion) {
        return publicacionRepository.save(newPublicacion);
    }*/

    // Método corregido
    public Publicacion createPublicacion(Publicacion newPublicacion) {
        // 1. Asignar fecha del servidor automáticamente (Más seguro)
        if (newPublicacion.getFechaPublicacion() == null) {
            newPublicacion.setFechaPublicacion(LocalDateTime.now()); // Requiere import java.time.LocalDateTime;
        }

        // 2. Inicializar likes en 0 si vienen nulos
        if (newPublicacion.getLikes() == null) {
            newPublicacion.setLikes(0);
        }

        return publicacionRepository.save(newPublicacion);
    }

    public Publicacion findById(Long id) {
        return publicacionRepository.findById(id)
                .orElseThrow(() -> new PublicacionNotFoundException(id));
    }

    public void deletePublicacion(Long id) {
        if (publicacionRepository.existsById(id)) {
            publicacionRepository.deleteById(id);
        } else {
            throw new PublicacionNotFoundException(id);
        }
    }

    public Publicacion updatePublicacion(Publicacion publicacion, Long id) {
        return publicacionRepository.findById(id)
                .map(existing -> {
                    existing.setTitulo(publicacion.getTitulo());
                    existing.setTipo(publicacion.getTipo());
                    existing.setFechaPublicacion(publicacion.getFechaPublicacion());
                    existing.setLikes(publicacion.getLikes());
                    existing.setUsuario(publicacion.getUsuario());
                    existing.setMascota(publicacion.getMascota());
                    return publicacionRepository.save(existing);
                })
                .orElseThrow(() -> new PublicacionNotFoundException(id));
    }
}