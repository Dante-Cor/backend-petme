package com.petme.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "fotos_mascota")

public class FotosMascota {
    //Instanciar variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fotos_mascotas")
    private Long id;

    @Column (name = "url_foto_mascota", nullable = false)
    private String urlFoto;

    @Column(name = "fecha_publicacion_foto", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime dateFoto;

    @Column(name = "orden_fotos_mascotas", nullable = false)
    private Integer ordenFoto;

    //Relación tablas Mascotas-Fotos Mascota
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fotos_id_mascotas")
    private Mascotas mascota;



    //Constructor
    public FotosMascota(Long id, String urlFoto, LocalDateTime dateFoto, Integer ordenFoto) {
        this.id = id;
        this.urlFoto = urlFoto;
        this.dateFoto = dateFoto;
        this.ordenFoto = ordenFoto;
    }

    public FotosMascota(){
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public LocalDateTime getDateFoto() {
        return dateFoto;
    }

    public void setDateFoto(LocalDateTime dateFoto) {
        this.dateFoto = dateFoto;
    }

    public Integer getOrdenFoto() {
        return ordenFoto;
    }

    public void setOrdenFoto(Integer ordenFoto) {
        this.ordenFoto = ordenFoto;
    }

    //toString
    @Override
    public String toString() {
        return "FotosMascota: " +
                "id: " + id +
                ", urlFoto: '" + urlFoto +
                ", dateFoto: " + dateFoto +
                ", ordenFoto: " + ordenFoto;
    }

    //equals y hashcode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FotosMascota that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(urlFoto, that.urlFoto) && Objects.equals(dateFoto, that.dateFoto) && Objects.equals(ordenFoto, that.ordenFoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlFoto, dateFoto, ordenFoto);
    }
}
