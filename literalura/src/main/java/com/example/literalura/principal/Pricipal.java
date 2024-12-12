package com.example.literalura.principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import com.example.literalura.service.ComsumoAPI;
import com.example.literalura.service.ConvierteDatos;

import java.util.*;

public class Pricipal {
    private Scanner teclado = new Scanner(System.in);
    private ComsumoAPI consumoAPI = new ComsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository repositorio;
    private final AutorRepository autorRepositorio;
    private List<Libro>libros;
    private List<Autor>autores;

    public Pricipal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ********************
                    Elija la opción que deseas consultar:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            }catch (InputMismatchException e){
                System.out.println("\nOpción inválida. Por favor, ingrese un número.\n");
                teclado.nextLine();
                continue;
            }
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion...");
                    break;
                default:
                    System.out.println("\nOpcion invalida. Ingrese otra opcion\n");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datos.librosLista().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isEmpty()) {
            System.out.println("\nLibro no encontrado\n");
        } else {
            DatosLibro primerLibro = libroBuscado.get();
            guardarLibroEnBaseDeDatos(primerLibro);
            }
        }
    private void guardarLibroEnBaseDeDatos(DatosLibro datosLibro) {
        Libro libroExistente = repositorio.findByTitulo(datosLibro.titulo());
        if (libroExistente != null) {
            System.out.println("\nEl libro ya esta registrado, no se puede registrar mas de una vez.\n");
            return;
        }
        Libro libro = new Libro(datosLibro);
        for (DatosAutor datosAutor : datosLibro.autor()) {
            Autor autorExistente = autorRepositorio.findByNombre(datosAutor.nombre());
            if (autorExistente == null) {
                Autor autor = new Autor(datosAutor.nombre(), datosAutor.fechaDeNacimiento(), datosAutor.fechaDeFallecimiento());
                autorRepositorio.save(autor);
                libro.agregaAutor(autor);
            }
        }
        repositorio.save(libro);

        System.out.println("\nLibro registrado con exito.\n");
        System.out.println(libro);
    }

    private void listarLibrosRegistrados() {
        libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepositorio.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }
    private void listarAutoresVivosPorAnio() {
        System.out.println("Indique el año que desea listar autores vivos:");
        var anio = teclado.nextInt();
        teclado.nextLine();
        autores = autorRepositorio.autoresVivosPorDeterminadoAnio(anio);
        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
            System.out.println("""
                    ********************
                    Seleccione el idioma por el que desea listar libros:
                    1 - INGLES
                    2 - ESPAÑOL
                    3 - PORTUGUES
                    ---------------------
                    """);
            var opcion = teclado.nextInt();
            teclado.nextLine();
            Idioma idioma = null;
            switch (opcion){
                case 1:
                    idioma = Idioma.INGLES;
                    break;
                case 2:
                    idioma = Idioma.ESPAÑOL;
                    break;
                case 3:
                    idioma = Idioma.PORTUGUES;
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
        }
        List<Libro> librosPorIdioma = repositorio.findByIdioma(idioma);
        if (librosPorIdioma.isEmpty()) {
            System.out.println("\nNo se encontraron libros en el idioma seleccionado.\n");
        } else {
            librosPorIdioma.forEach(System.out::println);
        }
    }
}