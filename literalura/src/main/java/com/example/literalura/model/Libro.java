package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private double numeroDescargas;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "autor_libro",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autores = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idioma().get(0));
        this.numeroDescargas = datosLibro.numeroDescargas();
        this.autores = new ArrayList<>();
    }

    public void agregaAutor(Autor autor) {
        if (!autores.contains(autor)) {
            autores.add(autor);
        }
        if (this.autor == null) {
            this.autor = autor;
        }
        if (!autor.getLibro().contains(this)){
            autor.agregaLibro(this);
        }
    }
    public Long getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
    @Override
    public String toString() {
        return "--------LIBRO--------\n" +
                "  Título: " + titulo + "\n" +
                "  Autor: " + autores.stream().map(Autor::getNombre).toList() + "\n" +
                "  Idioma: " + idioma + "\n" +
                "  Número de descargas: " + numeroDescargas + "\n" +
                "----------------------------\n";
    }
}







