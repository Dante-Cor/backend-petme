package com.petme.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password", "email", "telephone"}) // Ocultamos datos sensibles
    private User autor;

    // Relación inversa (necesaria para que Hibernate sepa a quién pertenece)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id")
    @com.fasterxml.jackson.annotation.JsonIgnore // Evita bucle infinito al convertir a JSON
    private Publicacion publicacion;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    // Constructor vacío
    public Comentario() {}

    // Constructor simple
    public Comentario(String texto, User autor, Publicacion publicacion) {
        this.texto = texto;
        this.autor = autor;
        this.publicacion = publicacion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public User getAutor() { return autor; }
    public void setAutor(User autor) { this.autor = autor; }
    public Publicacion getPublicacion() { return publicacion; }
    public void setPublicacion(Publicacion publicacion) { this.publicacion = publicacion; }
}