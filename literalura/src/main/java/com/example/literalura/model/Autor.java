package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;
    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro = new ArrayList<>();

    public Autor() {
    }

    public Autor(String nombre, String fechaDeNacimiento, String fechaDeFallecimiento) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public void agregaLibro(Libro libros) {
        if (!libro.contains((libro))) {
            libro.add(libros);
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }
    @Override
    public String toString() {
        return "--------AUTOR----------\n" +
                "  Nombre: " + nombre + "\n" +
                "  Fecha de Nacimiento: " + fechaDeNacimiento + "\n" +
                "  Fecha de Fallecimiento: " + fechaDeFallecimiento + "\n" +
                "  Libros: " + libro.stream().map(Libro::getTitulo).toList() + "\n" +
                "---------------------------\n";
    }
}
