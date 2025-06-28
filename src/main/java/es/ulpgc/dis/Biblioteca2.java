package es.ulpgc.dis;

import java.time.Instant;
import java.util.List;


public interface Biblioteca2 {
    List<User> users();
    List<Book> books();
    List<Ejemplar> ejemplares();
    List<Prestamo> prestamos();
    List<Devolucion> devoluciones();

    enum Genre{Drama, Comedy, Horror};
    record Book(String ISBN, String title, String author, Instant anoPub, Genre genre){}
    record User(String name, String email, Instant fechaReg){}
    record Ejemplar(String id, Book book){}
    record Prestamo(Instant fechaInicio, Instant fechaDevolucion, String id, User user, Ejemplar ejemplar){}
    record Devolucion(Instant ts, Prestamo prestamo){}
}
