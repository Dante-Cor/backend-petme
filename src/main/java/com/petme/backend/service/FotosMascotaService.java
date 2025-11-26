package com.petme.backend.service;

import com.petme.backend.exceptions.FotosMascotaNotFoundException;
import com.petme.backend.model.FotosMascota;
import com.petme.backend.repository.FotosMascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FotosMascotaService {
    //Inyectar clase
    private final FotosMascotaRepository fotosMascotaRepository;

    @Autowired
    public FotosMascotaService(FotosMascotaRepository fotosMascotaRepository) {
        this.fotosMascotaRepository = fotosMascotaRepository;
    }

    //Métodos
    // Mostrar todas las fotos
    public List<FotosMascota> getFotosMascotas() {
        return fotosMascotaRepository.findAll();
    }

    // Crear nueva foto
    public FotosMascota createFotoMascota(FotosMascota nuevaFoto) {
        // FIX: Asignar la fecha actual automáticamente porque la BD la exige (NOT NULL)
        if (nuevaFoto.getDateFoto() == null) {
            nuevaFoto.setDateFoto(LocalDateTime.now());
        }

        return fotosMascotaRepository.save(nuevaFoto);
    }

    // Mostrar por Id
    public FotosMascota findById(Long id) {
        return fotosMascotaRepository.findById(id)
                .orElseThrow(() -> new FotosMascotaNotFoundException(id));
    }

    // Eliminar por Id
    public void deleteFotoMascota(Long id) {
        if (fotosMascotaRepository.existsById(id)) {
            fotosMascotaRepository.deleteById(id);
        } else {
            throw new FotosMascotaNotFoundException(id);
        }
    }

    // Actualizar
    public FotosMascota updateFotoMascota(FotosMascota fotoMascota, Long id) {
        return fotosMascotaRepository.findById(id)
                .map(fotoMap -> {
                    fotoMap.setUrlFoto(fotoMascota.getUrlFoto());
                    fotoMap.setDateFoto(fotoMascota.getDateFoto());
                    fotoMap.setOrdenFoto(fotoMascota.getOrdenFoto());
                    return fotosMascotaRepository.save(fotoMap);
                })
                .orElseThrow(() -> new FotosMascotaNotFoundException(id));
    }

}
