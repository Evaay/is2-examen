package es.ulpgc.dis.Biblioteca;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import es.ulpgc.dis.Biblioteca.Biblioteca.*;


public record BibliotecaService(Biblioteca biblioteca) {
    //Obtener todos los libros disponibles (no prestados actualmente).
    List<Biblioteca.Book> librosDisponibles() {
        List<PrestamoStatus> prestamosRecientes = biblioteca.prestamosStatus().stream() // recorrer todos los prestamosStatus registrados en la biblioteca
                .collect(Collectors.groupingBy( //agrupar los prestamos estatus por el isbn del libro, para q sea unico
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(), //agrupamos por libro
                        Collectors.maxBy(Comparator.comparing(ps -> ps.prestamo().fechaInicio()))
                        // dentro de cada grupo, se queda solo con el prestamoStatus q tenga la fecha de inicio mas reciente
                ))
                .values() //tomar los valores del Optional<PrestamoStatus>
                .stream() //los pasa a stream para continuar el procesamiento
                .filter(prestamoStatus -> prestamoStatus.isPresent()) //asegurarnos q los valores de tipo optinal tienen un valor
                .map(prestamoStatus -> prestamoStatus.get()) //llamamos a get para extraer ese valor optional
                .toList();

        //Libros actualmente prestados
        List<Biblioteca.Book> librosPrestados = prestamosRecientes.stream()
                .filter(prestamoStatus -> prestamoStatus.prestamoState() == PrestamoState.activo)
                .map(prestamoStatus -> prestamoStatus.prestamo().book())
                .toList();

        System.out.println("Libros prestados:");
        librosPrestados.forEach(System.out::println);


        return biblioteca.catalog().stream()
                .filter(book -> !cointainsBook(book, librosPrestados))
                .toList();
    }

    boolean cointainsBook(Book book, List<Book> booksPrestados){
        for (Book prestado: booksPrestados) {
            if (prestado.isbn().equals(book.isbn())) {
                return true;
            }
        }
        return false;
    }

//    List<Biblioteca.Book> librosDisponibles2() {
//        Map<String, PrestamoStatus> recientes = biblioteca.prestamosStatus().stream()
//                .collect(Collectors.groupingBy(prestamoStatus -> prestamoStatus.prestamo().book().isbn()))
//                .entrySet()
//                .stream()
//                .collect(Collectors.toMap(
//                        Entry::getKey,
//                        entry -> entry.getValue().stream().max(Comparator.comparing(prestamoStatus -> prestamoStatus.prestamo().fechaInicio()))
//                                .orElse(null)
//                ));
//
//        return biblioteca.catalog().stream()
//                .filter(book -> isValid(book, recientes))
//                .toList();
//    }




    void prestamosRecientes() {
        List<PrestamoStatus> prestamosRecientes = biblioteca.prestamosStatus().stream() // recorrer todos los prestamosStatus registrados en la biblioteca
                .collect(Collectors.groupingBy( //agrupar los prestamos estatus por el isbn del libro, para q sea unico
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(), //agrupamos por libro
                        Collectors.maxBy(Comparator.comparing(ps -> ps.prestamo().fechaInicio()))
                        // dentro de cada grupo, se queda solo con el prestamoStatus q tenga la fecha de inicio mas reciente
                ))
                .values() //tomar los valores del Optional<PrestamoStatus>
                .stream() //los pasa a stream para continuar el procesamiento
                .filter(prestamoStatus -> prestamoStatus.isPresent()) //asegurarnos q los valores de tipo optinal tienen un valor
                .map(prestamoStatus -> prestamoStatus.get()) //llamamos a get para extraer ese valor optional
                .toList();
        System.out.println("Préstamos más recientes por libro:");
        prestamosRecientes.forEach(ps -> {
            System.out.printf(
                    "Libro: %s | Usuario: %s | Estado: %s | Fecha: %s\n",
                    ps.prestamo().book().title(),
                    ps.prestamo().user().nombre(),
                    ps.prestamoState(),
                    ps.prestamo().fechaInicio()
            );
        });

        Map<String, Optional<PrestamoStatus>> prueba = biblioteca.prestamosStatus().stream() // recorrer todos los prestamosStatus registrados en la biblioteca
                .collect(Collectors.groupingBy( //agrupar los prestamos estatus por el isbn del libro, para q sea unico
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(), //agrupamos por libro
                        Collectors.maxBy(Comparator.comparing(ps -> ps.prestamo().fechaInicio()))
                        // dentro de cada grupo, se queda solo con el prestamoStatus q tenga la fecha de inicio mas reciente
                ));

        Map<String, PrestamoStatus> limpio = prueba.entrySet().stream()  // Paso 1: recorremos cada entrada del mapa
                .filter(entry -> entry.getValue().isPresent())             // Paso 2: nos quedamos con los Optional que tienen valor
                .collect(Collectors.toMap(                                 // Paso 3: convertimos a nuevo mapa
                        Map.Entry::getKey,                                     // Clave: la misma clave original (el ISBN)
                        entry -> entry.getValue().get()                        // Valor: el PrestamoStatus dentro del Optional
                ));
    }

    // Listar todos los libros de un autor específico, ordenados por año de publicación descendente
    List<Book> librosAutor (String autor) {
        return biblioteca.catalog().stream()
                .filter(book -> book.autor().equals(autor))
                .sorted(Comparator.comparing(Book::anoPublicacion).reversed())
                .toList();
    }

    // Mostrar los 5 libros mas prestados -> 2 libros
    List<Book> masPrestados() {
        Map<Book, Long> mapaLibros = biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(
                        prestamoStatus -> prestamoStatus.prestamo().book(),
                        Collectors.counting()
                ));
        System.out.println(mapaLibros);
        return mapaLibros.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(2)
                .map(entry -> entry.getKey())
                .toList();
    }

    // Calcular el promedio de días de préstamo de todos los préstamos completados.
    Double promedioDiaPrestamo() {
        /*return biblioteca.prestamosStatus().stream()
                .filter(prestamoStatus -> prestamoStatus.prestamoState() == PrestamoState.devuelto)
                .mapToLong(ps -> ChronoUnit.DAYS.between(
                        ps.prestamo().fechaInicio(),
                        ps.prestamo().fechaDevolucion()
                ))
                .average()
                .orElse(0.0);*/

        List<Long> dias = biblioteca.prestamosStatus().stream()
                .filter(prestamoStatus -> prestamoStatus.prestamoState() == PrestamoState.devuelto)
                .map(prestamoStatus -> ChronoUnit.DAYS.between(
                        prestamoStatus.prestamo().fechaInicio(),
                        prestamoStatus.prestamo().fechaDevolucion()
                ))
                .toList();

        long suma = dias.stream().mapToLong(ps -> ps.longValue()).sum();
        return (double) (suma/dias.size());
    }

    //Listar los usuarios más activos (mayor cantidad de préstamos).
    List<String> userMasActivo() {
        return biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(prestamoStatus -> prestamoStatus.prestamo().user().nombre(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    //Agrupar los libros por género.
    Map<Genre, List<Book>> filtrarLibrosGenero() {
        return biblioteca.catalog().stream()
                .collect(Collectors.groupingBy(book -> book.genre()));
    }

    //Obtener una lista de títulos únicos de libros prestados en el último mes.
    List<Book> librosPrestados() {
        Instant mes = Instant.now().minus(30, ChronoUnit.DAYS);
        System.out.println(mes);
        return biblioteca.prestamosStatus().stream()
                .filter(prestamoStatus -> prestamoStatus.prestamo().fechaInicio().isAfter(mes))
                .map(prestamoStatus -> prestamoStatus.prestamo().book())
                .distinct()
                .toList();
    }

    //Dado un usuario, obtener todos los títulos de libros que ha leído.
    List<Book> librosLeidosUser(String userName) {
        return biblioteca.prestamosStatus().stream()
                .filter(prestamoStatus -> prestamoStatus.prestamoState() == PrestamoState.devuelto)
                .filter(prestamoStatus -> prestamoStatus.prestamo().user().nombre().equals(userName))
                .map(prestamoStatus -> prestamoStatus.prestamo().book())
                .distinct()
                .toList();

    }

    //Libro mas prestado
    Book libroMasPrestado() {
        Optional<String> bookISBN = biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Entry::getKey);

        return bookISBN.flatMap(
                isbn -> biblioteca.catalog().stream()
                        .filter(book -> book.isbn().equals(isbn))
                        .findFirst()
        ).orElse(null);
    }

    List<Book> prestamosMasRecientes() {
        Map<String, PrestamoStatus> prestamosPorLibros = biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(),
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(PrestamoStatus::ts)),
                                Optional::get // Esto es seguro solo si sabes que siempre habrá al menos un préstamo por libro
                        )
                ));

        Optional<PrestamoStatus> prestamosrecientes = biblioteca.prestamosStatus().stream()
                .sorted((e1, e2) -> e2.ts().compareTo(e1.ts()))
                .findFirst();

        Map<String, Optional<PrestamoStatus>> prestamosPorLibros2 = biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(
                        prestamoStatus -> prestamoStatus.prestamo().book().isbn(),
                        Collectors.maxBy(Comparator.comparing(PrestamoStatus::ts)))
                );

        return biblioteca.catalog().stream()
                .filter(book -> isValid(book, prestamosPorLibros))
                .toList();
    }

    private boolean isValid(Book book, Map<String, PrestamoStatus> prestamosPorLibros) {
        if (prestamosPorLibros.containsKey(book.isbn()) && (prestamosPorLibros.get(book.isbn()).prestamoState() == PrestamoState.activo)) {
            return false;
        }
        return true;
    }

    List<Biblioteca.Book> librosdispo(){
        Map<String, Optional<Biblioteca.PrestamoStatus>> firstprestamo = biblioteca.prestamosStatus().stream()
                .collect(Collectors.groupingBy(prestamoStatus -> prestamoStatus.prestamo().book().isbn(),
                        Collectors.maxBy((p1, p2) -> p2.ts().compareTo(p1.ts()))));

        return biblioteca.catalog().stream()
                .filter(book -> isValid2(book, firstprestamo))
                .toList();
    }

    private boolean isValid2(Biblioteca.Book book, Map<String, Optional<Biblioteca.PrestamoStatus>> firstprestamo) {
        Optional<Biblioteca.PrestamoStatus> prestamo = firstprestamo.get(book.isbn());

        if (prestamo == null || prestamo.isEmpty()) {
            return true;
        }

        // Si el último préstamo NO está activo, el libro está disponible
        return prestamo.get().prestamoState() != Biblioteca.PrestamoState.activo;
    }


}
