package com.petme.backend.service;

import com.petme.backend.model.Mascotas;
import com.petme.backend.repository.MascotasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotasService {
    private final MascotasRepository mascotasRepository;

    public MascotasService(MascotasRepository mascotasRepository){
        this.mascotasRepository = mascotasRepository;
    }

    public List<Mascotas> getAllMascotas(){
        return mascotasRepository.findAll();
    }
}
