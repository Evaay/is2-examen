package es.ulpgc.dis;

import java.util.List;
import java.util.stream.Collectors;

import es.ulpgc.dis.Biblioteca2.Book;

public record BibliotecaService2(Biblioteca2 biblioteca2) {
    void librosDisponibles() {
        List<String> ejemplaresPrestados = biblioteca2.prestamos().stream()
                .filter(prestamo -> biblioteca2.devoluciones().stream()
                        .noneMatch(devolucion -> devolucion.prestamo().equals(prestamo)))
                .map(prestamo -> prestamo.ejemplar().id())
                .toList();

        biblioteca2.ejemplares().stream()
                .filter(ejemplar -> !ejemplaresPrestados.contains(ejemplar.id()))
                .map(Biblioteca2.Ejemplar::book)
                .distinct()
                .forEach(libro -> System.out.println("Disponible: " + libro.title() + libro.));
    }

}
