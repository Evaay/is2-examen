package es.ulpgc.dis.Biblioteca2;

import es.ulpgc.dis.Biblioteca2.Biblioteca2.Book;
import es.ulpgc.dis.Biblioteca2.Biblioteca2.Genre;
import es.ulpgc.dis.Biblioteca2.Biblioteca2.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public record BibliotecaService2(Biblioteca2 biblioteca2) {
    //Obtener todos los libros disponibles
    void librosDisponibles() {
        List<Biblioteca2.Prestamo> ejemplaresPrestados = biblioteca2.prestamos().stream()
                .filter(prestamo -> notExistDevolution(prestamo))
                .toList();
        biblioteca2.ejemplares().stream()
                .filter(ejemplar -> isValid(ejemplar, ejemplaresPrestados))
                .map(ejemplar -> ejemplar.id())
                .forEach(ejemplarId -> System.out.println(ejemplarId));
    }

    private boolean isValid(Biblioteca2.Ejemplar ejemplar, List<Biblioteca2.Prestamo> ejemplaresPrestados) {
        int estaPrestado = (int) ejemplaresPrestados.stream()
                .filter(prestamo -> prestamo.ejemplar().id().equals(ejemplar.id()))
                .count();
        return estaPrestado == 0;
    }

    private boolean notExistDevolution(Biblioteca2.Prestamo prestamo) {
        int numDevolucion = (int) biblioteca2.devoluciones().stream()
                .filter(devolucion -> (devolucion.prestamo().id().equals(prestamo.id())))
                .count();
        return numDevolucion == 0;
    }

    //Listar todos los libros de un author especifico, ordenados por año de publicacion descendente
    List<Biblioteca2.Book> librosDe(String autor) {
        return biblioteca2.ejemplares().stream()
                .filter(ejemplar -> ejemplar.book().author().equals(autor))
                .map(ejemplar -> ejemplar.book())
                .distinct()
                .sorted(Comparator.comparing(Biblioteca2.Book::anoPub).reversed())
                .toList();
    }
    //Mostrar los 5 libros mas prestados
    List<Biblioteca2.Book> librosMasPrestados() {
        return biblioteca2.prestamos().stream()
                .collect(Collectors.groupingBy(
                        prestamo -> prestamo.ejemplar().book(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> entry.getKey())
                .limit(1)
                .toList();
    }
    //Calcular el promedio de dias de prestamos de todos los prestamos completados
    Long promedioDias() {
        return (long) biblioteca2.devoluciones().stream()
                .mapToLong(devolucion -> ChronoUnit.DAYS.between(
                        devolucion.prestamo().fechaInicio(), devolucion.ts()
                ))
                .average()
                .orElse(0.0);
    }
    //Listar los usuarios mas activos(mayor cantidad de prestamos)
    List<User> userMasActivos() {
        return biblioteca2.prestamos().stream()
                .collect(Collectors.groupingBy(
                        prestamo -> prestamo.user(),
                        Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> entry.getKey())
                .toList();
    }
    //los q no han hecho prestamos tmb
    List<User> userMasActivosSinPrestamoTmb() {
        return biblioteca2.users().stream()
                .collect(Collectors.toMap(
                        user -> user,
                        user -> biblioteca2.prestamos().stream()
                                .filter(prestamo -> prestamo.user().equals(user))
                                .count()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(entry -> entry.getKey())
                .toList();
    }
    //Agrupar los libros por genero
    Map<Genre, List<String>> librosPorGenero() {
        return biblioteca2.books().stream()
                .collect(Collectors.groupingBy(
                        book -> book.genre(),
                        Collectors.mapping(book -> book.title(), Collectors.toList())));
    }
    //Obtener una lista de titulos unicos de libros prestados en el ultimo mes
    List<Book> titulosUnicosLibrosPrestadosUltimoMes() {
        Instant lastMonth = LocalDate.of(2025, 5, 29).atStartOfDay(ZoneId.systemDefault()).toInstant();
        return biblioteca2.prestamos().stream()
                .filter(prestamo -> prestamo.fechaInicio().isAfter(lastMonth))
                .map(prestamo -> prestamo.ejemplar().book())
                .distinct()
                .toList();
    }
    //Dado un usuario, obtener todos los titulos de libros que ha leido
    List<String> titulosLibrosLeidos(User user) {
        return biblioteca2.devoluciones().stream()
                .filter(devoluciones -> devoluciones.prestamo().user().equals(user))
                .map(devolucion -> devolucion.prestamo().ejemplar().book().title())
                .distinct()
                .toList();
    }
}
