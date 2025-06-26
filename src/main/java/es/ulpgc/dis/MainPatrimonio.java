package es.ulpgc.dis;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MainPatrimonio {
    public static void main(String[] args) {
        PatrimonioHistorico patrimonio = new PatrimonioHistorico() {
            @Override
            public List<ElementoPatrimonial> elementosPatrimoniales() {
                return List.of(
                        new ElementoPatrimonial(
                                "Catedral de Mallorca",
                                "Antoni Gaudí",
                                Instant.parse("1300-01-01T00:00:00Z"),
                                new Localizacion("Palma de Mallorca"),
                                CatalogType.Iglesias,
                                MetodoFabricacion.Amano,
                                MaterialType.Madera,
                                EstadoConservacion.Bien
                        ),
                        new ElementoPatrimonial(
                                "Estatua de Miguel de Cervantes",
                                "José Alcoverro",
                                Instant.parse("1892-01-01T00:00:00Z"),
                                new Localizacion("Madrid"),
                                CatalogType.Estatuas,
                                MetodoFabricacion.Amano,
                                MaterialType.Piedra,
                                EstadoConservacion.Excelente
                        ),
                        new ElementoPatrimonial(
                                "Grafiti 'El beso'",
                                "Pez",
                                Instant.parse("2010-01-01T00:00:00Z"),
                                new Localizacion("Barcelona"),
                                CatalogType.Grafitis,
                                MetodoFabricacion.Amano,
                                MaterialType.Piedra,
                                EstadoConservacion.Mal
                        )
                );
            }

            @Override
            public List<Fotografia> fotografias() {
                List<ElementoPatrimonial> elementos = elementosPatrimoniales();
                return List.of(
                        new Fotografia(elementos.get(0)),
                        new Fotografia(elementos.get(1)),
                        new Fotografia(elementos.get(2))
                );
            }

            @Override
            public List<FotografiaStatus> fotografiaStatus() {
                List<Fotografia> fotos = fotografias();
                return List.of(
                        new FotografiaStatus(Instant.parse("2020-01-01T00:00:00Z"), fotos.get(0)),
                        new FotografiaStatus(Instant.parse("2021-05-15T00:00:00Z"), fotos.get(1)),
                        new FotografiaStatus(Instant.parse("2022-11-20T00:00:00Z"), fotos.get(2))
                );
            }

            @Override
            public List<Recorrido> recorridos() {
                List<ElementoPatrimonial> elementos = elementosPatrimoniales();
                return List.of(
                        new Recorrido(new Localizacion("Palma de Mallorca"), elementos.get(0)),
                        new Recorrido(new Localizacion("Madrid"), elementos.get(1)),
                        new Recorrido(new Localizacion("Barcelona"), elementos.get(2))
                );
            }
        };

        PatrimonioService patrimonioService = new PatrimonioService(patrimonio);
        patrimonioService.elementosExcelentes().forEach(p -> System.out.println(p.name() + ": " + p.estado()));
        patrimonioService.elementosDespues1900().forEach(p -> System.out.println(p.name() + ": " + p.ano()));
        patrimonioService.autorAntoni().forEach(p -> System.out.println(p.name() + ": " + p.autor()));
        patrimonioService.nombresElementos().forEach(p -> System.out.println("Nombre: " + p));
        patrimonioService.soloNombreAno().forEach(p -> System.out.println("NombreAño: " + p.name() + " " + p.ano()));
        patrimonioService.nombresMayuscula().forEach(p -> System.out.println("NombreMayus: " + p));
        patrimonioService.elementoPorAno().forEach(p -> System.out.println("OrdenAño: " + p.name() + " " + p.ano()));
        patrimonioService.elementoPorName().forEach(p -> System.out.println("OrdenNombreAlfabeto: " + p.name()));
        patrimonioService.elementoAntiguo().forEach(p -> System.out.println("OrdenMasAntiguo: " + p.name() + " " + p.ano()));
        System.out.println("Total patromino: " + patrimonioService.numTotalElementos());
        System.out.println("Año mas reciente: " + patrimonioService.anoMasReciente());
        System.out.println("Promedio de años: " + patrimonioService.promedioAnosAntiguedad());
        patrimonioService.elementosPorTipo().forEach((catalogType, elementoPatrimonials) -> System.out.println("por tipo: " + catalogType + " -> " + elementoPatrimonials));
        patrimonioService.elementosPorMaterial().forEach((materialType, num) -> System.out.println("por numero: " + materialType + " -> " + num));
        System.out.println("antes2000: " + patrimonioService.todosnatesde2000());
        System.out.println("primer nombre Catedral: " + patrimonioService.primerCatedral().name());
        System.out.println(patrimonioService.anosFabricacion());
        System.out.println(patrimonioService.anosFabricacion2());
        System.out.println(patrimonioService.numRecorridoAntesSiglo());
    }
}
