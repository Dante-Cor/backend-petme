package com.petme.backend.controller;

import com.petme.backend.model.Mascotas;
import com.petme.backend.service.MascotasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotasController {
    private final MascotasService mascotasService;

    private MascotasController(MascotasService mascotasService){
        this.mascotasService = mascotasService;
    }

    @GetMapping
    public List<Mascotas> getMascotas(){
        return mascotasService.getAllMascotas();
    }

}
