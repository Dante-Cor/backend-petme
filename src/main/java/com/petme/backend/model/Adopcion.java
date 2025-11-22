package com.petme.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="adoptions")
public class Adopcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_adoptions")
    private Long id;


    @Column(name="data_app",nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date_Application;


    @Column(name="date_auth",nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime date_authorization;


    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private String comments;
    // Relación con User (Un usuario puede tener muchas adopciones)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_user", nullable = false)
    private User user;

    // Relación con Mascota (Una mascota puede tener muchas solicitudes de adopción)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascotas_id_mascotas", nullable = false)
    private Mascotas mascota;


    // Constructor completo
    public Adopcion(Long id, LocalDateTime date_Application, LocalDateTime date_authorization,
                    String estado, String comments) {
        this.id = id;
        this.date_Application = date_Application;
        this.date_authorization = date_authorization;
        this.estado = estado;
        this.comments = comments;
    }

    // Constructor vacío (necesario para JPA/Hibernate)
    public Adopcion(){

    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate_Application() {
        return date_Application;
    }

    public void setDate_Application(LocalDateTime date_Application) {
        this.date_Application = date_Application;
    }

    public LocalDateTime getDate_authorization() {
        return date_authorization;
    }

    public void setDate_authorization(LocalDateTime date_authorization) {
        this.date_authorization = date_authorization;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // toString

    @Override
    public String toString() {
        return "Adopcion{" + // <-- Cambiado de User a Adopcion
                "id=" + id +
                ", date_Application=" + date_Application +
                ", date_authorization=" + date_authorization +
                ", estado='" + estado + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }

    // Hashcode y equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adopcion adopcion = (Adopcion) o;

        return Objects.equals(id, adopcion.id) && Objects.equals(date_Application, adopcion.date_Application) && Objects.equals(date_authorization, adopcion.date_authorization) && Objects.equals(estado, adopcion.estado) && Objects.equals(comments, adopcion.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_Application, date_authorization, estado, comments);
    }
}