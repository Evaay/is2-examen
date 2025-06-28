package es.ulpgc.dis;

import java.util.List;
import java.util.stream.Collectors;

import es.ulpgc.dis.Biblioteca2.Book;

public record BibliotecaService2(Biblioteca2 biblioteca2) {
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
}
