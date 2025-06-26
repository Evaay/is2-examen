package es.ulpgc.dis;

import java.time.Instant;
import java.util.List;

public interface Biblioteca {
    List<User> users();
    List<Prestamo> prestamos();
    List<PrestamoStatus> prestamosStatus();
    List<Book> catalog();

    record User(String nombre, String email, Instant fechaReg, Prestamo prestamo){}
    record Prestamo(User user, Book book, Instant fechaInicio, Instant fechaDevolucion){}
    record Book(String isbn, String title, String autor, Instant anoPublicacion, Genre genre){}
    enum Genre{Comedy, Action, Horror, Romance}
    enum PrestamoState {activo, devuelto}
    record PrestamoStatus(Prestamo prestamo, PrestamoState prestamoState, Instant ts){}

}
