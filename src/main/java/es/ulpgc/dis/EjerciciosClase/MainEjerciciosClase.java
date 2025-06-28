package es.ulpgc.dis.EjerciciosClase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainEjerciciosClase {
    public static void main(String[] args) {
        /*
        Diccionario diccionario = new Diccionario();
        System.out.println(diccionario.wordAToM());
        System.out.println(diccionario.wordNToFinal());
        System.out.println(diccionario.agrupar3FirstLetter());
        System.out.println(diccionario.getPalindromos());
        System.out.println(diccionario.numVocales());
        System.out.println(diccionario.startAendZ());
        System.out.println(diccionario.longestWord());
        */

        List<Movie> peliculas = List.of(
                new Movie(1, "La Vida es Bella", 1997, "tt0118799",
                        List.of(new Movie.Genre(1, "Drama"), new Movie.Genre(2, "Comedia")),
                        List.of(new Movie.Director(1, "Roberto Benigni", "nm0000971"))
                ),
                new Movie(2, "El Gran Hotel Budapest", 2014, "tt2278388",
                        List.of(new Movie.Genre(2, "Comedia")),
                        List.of(new Movie.Director(2, "Wes Anderson", "nm0027572"))
                ),
                new Movie(3, "Parásitos", 2019, "tt6751668",
                        List.of(new Movie.Genre(1, "Drama"), new Movie.Genre(3, "Thriller")),
                        List.of(new Movie.Director(3, "Bong Joon-ho", "nm0094435"))
                ),
                new Movie(4, "El Discurso del Rey", 2010, "tt1504320",
                        List.of(new Movie.Genre(1, "Drama")),
                        List.of(new Movie.Director(4, "Tom Hooper", "nm0393799"))
                ),
                new Movie(5, "Intocable", 2011, "tt1675434",
                        List.of(new Movie.Genre(1, "Drama"), new Movie.Genre(2, "Comedia")),
                        List.of(new Movie.Director(5, "Olivier Nakache", "nm0619453"),
                                new Movie.Director(6, "Éric Toledano", "nm0865415"))
                ),
                new Movie(6, "Jojo Rabbit", 2019, "tt2584384",
                        List.of(new Movie.Genre(1, "Drama"), new Movie.Genre(2, "Comedia")),
                        List.of(new Movie.Director(7, "Taika Waititi", "nm0169806"))
                ),
                new Movie(7, "Gran Torino", 2008, "tt1205489",
                        List.of(new Movie.Genre(1, "Drama")),
                        List.of(new Movie.Director(8, "Clint Eastwood", "nm0000142"))
                ),
                new Movie(8, "Amélie", 2001, "tt0211915",
                        List.of(new Movie.Genre(2, "Comedia"), new Movie.Genre(1, "Drama")),
                        List.of(new Movie.Director(9, "Jean-Pierre Jeunet", "nm0000466"),
                                new Movie.Director(7, "Taika Waititi", "nm0169806"))
                )
        );

        MovieExercise movieExercise = new MovieExercise(peliculas);
        peliculas.stream()
                .flatMap(movie -> movie.directors().stream()
                        .map(director -> Map.entry(director, movie)))
                .forEach(System.out::println);
        peliculas.stream()
                .flatMap(movie -> movie.directors().stream()
                        .map(director -> director.name() + " -> " + movie.title()))
                .forEach(System.out::println);

        System.out.println(peliculas.stream()
                .flatMap(movie -> movie.directors().stream()
                        .map(director -> Map.entry(director, 1)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(num -> num.getValue())
                )));
        System.out.println(peliculas.stream()
                .flatMap(movie -> movie.directors().stream()
                        .map(director -> Map.entry(director, movie)))
                .collect(Collectors.groupingBy(
                        entry -> entry.getKey(),
                        Collectors.summingInt(movie -> 1)
                )));
        System.out.println("preuba");
        peliculas.stream()
                .flatMap(movie -> movie.directors().stream())
                .forEach(director -> System.out.println(director.name()));

        peliculas.stream()
                .flatMap(movie -> movie.directors().stream()
                        .map(director -> Map.entry(director, movie.genres())))
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().size()))
                .forEach(entry -> System.out.println(entry));
    }
}
