package com.petme.backend.controller;

import com.petme.backend.exceptions.PublicacionNotFoundException;
import com.petme.backend.model.Publicacion;
import com.petme.backend.service.PublicacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
@CrossOrigin(origins = "*")
public class PublicacionController {

    private final PublicacionService publicacionService;

    public PublicacionController(PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @GetMapping
    public List<Publicacion> findAllPublicaciones() {
        return publicacionService.getAllPublicaciones();
    }

    @PostMapping("/new-publicacion")
    public ResponseEntity<Publicacion> savePublicacion(@RequestBody Publicacion newPublicacion) {
        Publicacion saved = publicacionService.createPublicacion(newPublicacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/id-publicacion/{id}")
    public ResponseEntity<Publicacion> getById(@PathVariable Long id) {
        try {
            Publicacion pub = publicacionService.findById(id);
            return ResponseEntity.ok(pub);
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-publicacion/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            publicacionService.deletePublicacion(id);
            return ResponseEntity.noContent().build();
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-publicacion/{id}")
    public ResponseEntity<Publicacion> updateById(@RequestBody Publicacion publicacion, @PathVariable Long id) {
        try {
            Publicacion updated = publicacionService.updatePublicacion(publicacion, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(updated);
        } catch (PublicacionNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}