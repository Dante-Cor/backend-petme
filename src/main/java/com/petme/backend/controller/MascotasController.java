package com.petme.backend.controller;

import com.petme.backend.model.Mascotas;
import com.petme.backend.service.MascotasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
@CrossOrigin(origins = "*")
public class MascotasController {
    private final MascotasService mascotasService;

    @Autowired
    private MascotasController(MascotasService mascotasService){
        this.mascotasService = mascotasService;
    }

    @GetMapping
    public List<Mascotas> getMascotas(){
        return mascotasService.getAllMascotas();
    }

    // --- NUEVO MÉTODO POST ---
    // URL: POST /api/v1/mascotas?userId=1
    @PostMapping
    public ResponseEntity<Mascotas> saveMascota(
            @RequestBody Mascotas nuevaMascota, // Los datos del perro/gato
            @RequestParam Long userId) {        // El ID del dueño

        Mascotas mascotaGuardada = mascotasService.createMascota(nuevaMascota, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaGuardada);
    }

}
