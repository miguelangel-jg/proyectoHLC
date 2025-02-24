package com.example.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "contenido")
    private String contenido;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private Usuario usuario;

    public Publicacion() {
    }

    public Publicacion(String contenido, LocalDateTime fecha, Usuario usuario) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public Publicacion(Integer id, String contenido, LocalDateTime fecha, Usuario usuario) {
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Usuario getId_user() {
        return usuario;
    }

    public void setId_user(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() { // <-- Cambia el nombre del getter
        return usuario;
    }

    public void setUsuario(Usuario usuario) { // <-- Cambia el nombre del setter
        this.usuario = usuario;
    }

}
