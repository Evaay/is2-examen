package es.ulpgc.dis;

import java.time.Instant;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        BibliotecaService bibliotecaService = new BibliotecaService(new Biblioteca() {
            @Override
            public List<User> users() {
                return List.of(
                        new User("Alice", "alice@example.com", new Date().toInstant(), null),
                        new User("Bob", "bob@example.com", new Date().toInstant(), null),
                        new User("Charlie", "charlie@example.com", new Date().toInstant(), null),
                        new User("Juan", "juan@example.com", new Date().toInstant(), null)
                );
            }

            @Override
            public List<Prestamo> prestamos() {
                return List.of(
                        new Prestamo(
                                users().get(0), // Alice
                                catalog().get(0), // Libro 1
                                new Date(124, 0, 1).toInstant(), // 1 Enero 2024
                                new Date(124, 0, 15).toInstant()
                        ),
                        new Prestamo(
                                users().get(1), // Bob
                                catalog().get(0), // Libro 1 (mismo libro, préstamo más reciente)
                                new Date(124, 1, 1).toInstant(), // 1 Febrero 2024
                                new Date(124, 1, 15).toInstant()
                        ),
                        new Prestamo(
                                users().get(2), // Charlie
                                catalog().get(1), // Libro 2
                                new Date(124, 0, 10).toInstant(),
                                new Date(124, 0, 25).toInstant()
                        ),
                        new Prestamo(
                                users().get(2), // Charlie
                                catalog().get(2), // Libro 3
                                new Date(124, 0, 10).toInstant(),
                                new Date(124, 0, 25).toInstant()
                        ),
                        new Prestamo(
                                users().get(1), // Bob
                                catalog().get(2), // Libro 3
                                new Date(125, 6, 2).toInstant(),
                                new Date(125, 6, 10).toInstant()
                        )

                );
            }

            @Override
            public List<PrestamoStatus> prestamosStatus() {
                return List.of(
                        new PrestamoStatus(prestamos().get(0), PrestamoState.devuelto, Instant.parse("2024-01-16T00:00:00Z")),
                        new PrestamoStatus(prestamos().get(1), PrestamoState.activo, Instant.parse("2024-02-01T00:00:00Z")),
                        new PrestamoStatus(prestamos().get(2), PrestamoState.devuelto,  Instant.parse("2024-01-13T00:00:00Z")),
                        new PrestamoStatus(prestamos().get(3), PrestamoState.activo,  Instant.parse("2024-04-02T00:00:00Z")),
                        new PrestamoStatus(prestamos().get(4), PrestamoState.activo,  Instant.parse("2025-06-20T00:00:00Z")));
            }

            @Override
            public List<Book> catalog() {
                return List.of(
                        new Book("ISBN-001", "Libro 1", "Autor A", new Date().toInstant(), Genre.Action),
                        new Book("ISBN-002", "Libro 2", "Autor B", new Date().toInstant(), Genre.Romance),
                        new Book("ISBN-003", "Libro 3", "Autor c", new Date().toInstant(), Genre.Horror),
                        new Book("ISBN-004", "Libro 4", "Autor c", new Date(125, 8, 4).toInstant(), Genre.Comedy),
                        new Book("ISBN-005", "Libro 5", "Autor A", new Date(125, 8, 4).toInstant(), Genre.Comedy)
                );
            }
        });

        bibliotecaService.prestamosRecientes();
        System.out.println("libros disponibles 1: " + bibliotecaService.librosDisponibles());
        System.out.println(bibliotecaService.librosAutor("Autor c"));
        System.out.println(bibliotecaService.masPrestados());
        System.out.println(bibliotecaService.promedioDiaPrestamo());
        System.out.println(bibliotecaService.userMasActivo());
        //retorna una lista de nombres, no de user, pq la implementacion del equals no esta
        System.out.println(bibliotecaService.filtrarLibrosGenero());
        System.out.println(bibliotecaService.librosPrestados());
        System.out.println(bibliotecaService.librosLeidosUser("Charlie"));

        //System.out.println("libros disponibles 2: " + bibliotecaService.librosDisponibles2());
        System.out.println(bibliotecaService.libroMasPrestado());


        System.out.println("recientes: " + bibliotecaService.prestamosMasRecientes());
        System.out.println("recientes2: " + bibliotecaService.librosdispo());
    }
}