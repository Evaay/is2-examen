package es.ulpgc.dis;

import java.time.Instant;
import java.util.List;

public interface PatrimonioHistorico {
    List<ElementoPatrimonial> elementosPatrimoniales();
    List<Fotografia> fotografias();
    List<FotografiaStatus> fotografiaStatus();
    List<Recorrido> recorridos();

    record ElementoPatrimonial(String name,
                               String autor,
                               Instant ano,
                               Localizacion localizacion,
                               CatalogType tipo,
                               MetodoFabricacion metodoFab,
                               MaterialType material,
                               EstadoConservacion estado){}

    record Fotografia(ElementoPatrimonial elementoPatrimonial){}
    record FotografiaStatus(Instant ts, Fotografia fotografia){}
    record Localizacion(String ciudad){}
    record Recorrido(Localizacion localizacion, ElementoPatrimonial elementoPatrimonial){}
    enum CatalogType{Bustos, Estatuas, Iglesias, Edificios, Puentes, Grafitis}
    enum MetodoFabricacion{Amano, Maquina}
    enum MaterialType{Lana, Madera, Algodon, Piedra}
    enum EstadoConservacion{Bien, Mal, Excelente, Pobre}
}
