package es.ulpgc.dis.Biblioteca2;

import es.ulpgc.dis.Biblioteca2.Biblioteca2.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

public class MainBiblioteca2 {
    public static void main(String[] args) {
        Biblioteca2 biblioteca2 = new Biblioteca2() {
            @Override
            public List<User> users() {
                return List.of(
                        new User("Ana Pérez", "ana@example.com", fecha(2023, 5, 10)),
                        new User("Luis Gómez", "luis@example.com", fecha(2024, 1, 15)),
                        new User("Josefa Muñoz", "josefa@example.com", fecha(2024, 2, 9))
                );
            }

            @Override
            public List<Book> books() {
                return List.of(
                        new Book("978-84-376-0494-7", "La casa de los espíritus", "Isabel Allende", fecha(19821, 6, 1), Genre.Drama),
                        new Book("978-0-123-45678-9", "It", "Isabel Allende", fecha(1986, 9, 15), Genre.Horror),
                        new Book("978-3-16-148410-0", "El nombre de la rosa", "Umberto Eco", fecha(1980, 1, 20), Genre.Drama),
                        new Book("978-0-456-78901-2", "La vida es bella", "Roberto Benigni", fecha(1997, 3, 12), Genre.Comedy)
                );
            }

            @Override
            public List<Ejemplar> ejemplares() {
                List<Book> libros = books();
                return List.of(
                        new Ejemplar("EJ-001", libros.get(0)),
                        new Ejemplar("EJ-002", libros.get(1)),
                        new Ejemplar("EJ-003", libros.get(2)),
                        new Ejemplar("EJ-004", libros.get(3)),
                        new Ejemplar("EJ-005", libros.get(0)) // Segundo ejemplar del mismo libro
                );
            }

            @Override
            public List<Prestamo> prestamos() {
                List<User> usuarios = users();
                List<Ejemplar> ej = ejemplares();
                return List.of(
                        new Prestamo(fecha(2024, 4, 1), fecha(2024, 4, 20), "PR-001", usuarios.get(0), ej.get(0)),
                        new Prestamo(fecha(2024, 4, 5), fecha(2024, 5, 22), "PR-002", usuarios.get(1), ej.get(1)),
                        new Prestamo(fecha(2025, 6, 10), fecha(2024, 4, 22), "PR-003", usuarios.get(1), ej.get(0))
                        );
            }

            @Override
            public List<Devolucion> devoluciones() {
                List<Prestamo> pr = prestamos();
                return List.of(
                        new Devolucion(fecha(2024, 4, 21), pr.get(0)),
                        new Devolucion(fecha(2024, 4, 25), pr.get(1)),
                        new Devolucion(fecha(2024, 4, 25), pr.get(2))
                );
            }

            // Utilidad para crear fechas
            private Instant fecha(int anio, int mes, int dia) {
                return LocalDate.of(anio, mes, dia).atStartOfDay().toInstant(ZoneOffset.UTC);
            }
        };

        BibliotecaService2 bibliotecaService2 = new BibliotecaService2(biblioteca2);
        bibliotecaService2.librosDisponibles();
        System.out.println(bibliotecaService2.librosDe("Isabel Allende"));
        System.out.println(bibliotecaService2.librosMasPrestados());
        System.out.println(bibliotecaService2.promedioDias());
        System.out.println(bibliotecaService2.userMasActivos());
        System.out.println(bibliotecaService2.userMasActivosSinPrestamoTmb());
        System.out.println(bibliotecaService2.librosPorGenero());
        System.out.println(bibliotecaService2.titulosUnicosLibrosPrestadosUltimoMes());
        System.out.println(bibliotecaService2.titulosLibrosLeidos(biblioteca2.users().get(1)));
    }
}
