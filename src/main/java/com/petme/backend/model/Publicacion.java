package com.petme.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import java.util.ArrayList;

/*⚠️ Nota: Este modelo asume que User y Mascota existen dentro de  com.petme.backend.model. */

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicaciones")
    private Long id;

    @Column(name = "titulo_publicaciones", nullable = false, length = 255)
    private String titulo;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "fecha_publicacion", nullable = false)
    private LocalDateTime fechaPublicacion;

    @Column(name = "likes", nullable = false)
    private Integer likes;

    // Relación con User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id_usuario", nullable = false)
// Ya no es estrictamente necesario el JsonIgnoreProperties aquí, pero déjalo por seguridad
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // Relación con Adopcion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoptions_id_adoptions", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Adopcion adopcion;


    // Relación con Mascota
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "mascotas_id_mascotas")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Mascotas mascota;

    // --- 🔥 NUEVO: RELACIÓN CON COMENTARIOS ---
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comentario> comentarios = new ArrayList<>();


    // Constructors
    public Publicacion() {}

    public Publicacion(Long id, String titulo, String tipo, LocalDateTime fechaPublicacion, Integer likes, User usuario, Mascotas mascota) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaPublicacion = fechaPublicacion;
        this.likes = likes;
        this.user = usuario;
        this.mascota = mascota;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDateTime fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public Integer getLikes() { return likes; }
    public void setLikes(Integer likes) { this.likes = likes; }

    public User getUsuario() { return user; }
    public void setUsuario(User usuario) { this.user = usuario; }

    public Mascotas getMascota() { return mascota; }
    public void setMascota(Mascotas mascota) { this.mascota = mascota; }

    public Adopcion getAdopion() {return adopcion;}
    public void setAdopcion(Adopcion adopcion) {this.adopcion = adopcion;}

    // Getter y Setter
    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    @Override
    public String toString() {
        return "Publicacion{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", likes=" + likes +
                ", usuario=" + (user != null ? user.getUsername() : "null") +
                ", mascota=" + (mascota != null ? mascota.getNombre_mascotas() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publicacion)) return false;
        Publicacion that = (Publicacion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}