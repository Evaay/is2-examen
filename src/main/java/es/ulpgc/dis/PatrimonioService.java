package es.ulpgc.dis;

import org.w3c.dom.ls.LSInput;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public record PatrimonioService(PatrimonioHistorico patrimonioHistorico) {
    //Filtrar los elementos que están en un estado de conservación "Excelente".
    List<PatrimonioHistorico.ElementoPatrimonial> elementosExcelentes() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(elementoPatrimonial -> elementoPatrimonial.estado() == PatrimonioHistorico.EstadoConservacion.Excelente)
                .toList();
    }
    //Filtrar los elementos construidos después del año 1900.
    List<PatrimonioHistorico.ElementoPatrimonial> elementosDespues1900() {
        Instant ano1900 = Instant.parse("1900-01-01T00:00:00Z");
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(p -> p.ano().isAfter(ano1900))
                .toList();
    }
    //Filtrar los elementos cuyo autor sea "Antoni Gaudí".
    List<PatrimonioHistorico.ElementoPatrimonial> autorAntoni() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(p -> p.autor().equals("Antoni Gaudí"))
                .toList();
    }

    //Crear una lista de nombres de todos los elementos patrimoniales.
    List<String> nombresElementos() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .map(p -> p.name())
                .toList();
    }
    //Obtener una lista de objetos con solo el nombre y el año de fabricación de cada elemento.
    record elementoResumido(String name, Instant ano) {}
    List<elementoResumido> soloNombreAno() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .map(p -> new elementoResumido(p.name(), p.ano()))
                .toList();
    }
    //Convertir los nombres de los elementos a mayúsculas.
    List<String> nombresMayuscula() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .map(p -> p.name().toUpperCase())
                .toList();
    }

    //Ordenar los elementos por año de fabricación (de más antiguo a más moderno).
    List<PatrimonioHistorico.ElementoPatrimonial> elementoPorAno() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .sorted((p1, p2) -> p2.ano().compareTo(p1.ano()))
                .toList();
    }
    //Ordenar los elementos por nombre alfabéticamente. (desde la A a la z)
    List<PatrimonioHistorico.ElementoPatrimonial> elementoPorName() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .toList();
    }
    //Obtener los 5 elementos más antiguos.
    List<PatrimonioHistorico.ElementoPatrimonial> elementoAntiguo() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .sorted(Comparator.comparing(PatrimonioHistorico.ElementoPatrimonial::ano))
                .limit(2)
                .toList();
    }

    //Calcular el número total de elementos patrimoniales.
    Long numTotalElementos() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .count();
    }
    //Encontrar el año de fabricación más reciente.
    Instant anoMasReciente() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .map(PatrimonioHistorico.ElementoPatrimonial::ano)
                .sorted()
                .findFirst()
                .orElse(null);
    }
    //Calcular el promedio de años de antigüedad de los elementos.
    double promedioAnosAntiguedad() {
        LocalDate ahora = LocalDate.now(); // Cacheamos para consistencia

        return patrimonioHistorico.elementosPatrimoniales().stream()
                .mapToLong(p -> ChronoUnit.YEARS.between(
                        p.ano().atZone(ZoneId.systemDefault()).toLocalDate(),
                        ahora))
                .average()
                .orElse(0.0); // Usamos 0.0 (double) en lugar de 0 (int)
    }

    //Agrupar los elementos por tipo (Bustos, Estatuas, Iglesias, etc.).
    Map<PatrimonioHistorico.CatalogType, List<PatrimonioHistorico.ElementoPatrimonial>> elementosPorTipo() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .collect(Collectors.groupingBy(p -> p.tipo()));
    }
    //Crear un mapa donde la clave sea el autor y el valor sea la lista de sus obras.
    //Contar cuántos elementos hay de cada material (mármol, bronce, etc.).
    Map<PatrimonioHistorico.MaterialType, Long> elementosPorMaterial() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .collect(Collectors.groupingBy(
                        p -> p.material(),
                        Collectors.counting()));
    }

    //Verificar si hay al menos un elemento en estado "Pobre".
    boolean hayElementoPobre() {
        Long numElementoPobre = patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(p -> p.estado() == PatrimonioHistorico.EstadoConservacion.Pobre)
                .count();
        return numElementoPobre > 1;
    }

    //Verificar si todos los elementos son anteriores al año 2000.
    boolean todosnatesde2000(){
        Long elementosantes2000 = patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(p -> p.ano().isBefore(Instant.parse("2000-01-01T00:00:00Z")))
                .count();
        return elementosantes2000 == patrimonioHistorico.elementosPatrimoniales().size();
    }

    //Encontrar el primer elemento cuyo nombre empiece con "Catedral".
    PatrimonioHistorico.ElementoPatrimonial primerCatedral(){
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .filter(e -> primerNombre(e.name()).equals("Catedral"))
                .findFirst().orElse(null);
    }

    String primerNombre(String nombre) {
        String[] nombres = nombre.split(" ");
        return nombres[0];
    }

    //Concatenar los nombres de todos los elementos separados por ", ".
    //Calcular la suma de los años de fabricación de los elementos entre el q se construyo hasta la actualidad.
    int anosFabricacion() {
        return patrimonioHistorico.elementosPatrimoniales().stream()
                .forEach(elementoPatrimonial -> Collectors.summingInt(elementoPatrimonial.ano().truncatedTo(ChronoUnit.YEARS)));
    }
    //Obtener una lista única de todos los autores sin repetir.

    //Filtrar los recorridos que incluyan al menos un elemento del tipo "Estatuas".
    //Obtener una lista de nombres de elementos que pertenezcan a un recorrido con temática "Arte Moderno".
    //Contar cuántos recorridos incluyen elementos construidos antes del siglo XVIII.
}
