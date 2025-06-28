package es.ulpgc.dis.EjerciciosClase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Diccionario {
    List<String> diccionario = Arrays.asList(
            "a",
            "amor",
            "abeja",
            "abecedario",
            "acantilado",
            "aire",
            "alabanza",
            "alondra",
            "amapola",
            "amistad",
            "anís",
            "araña",
            "arroz",
            "azul",
            "averz",
            "balanza",
            "barco",
            "belleza",
            "cielo",
            "colina",
            "dado",
            "dedal",
            "elefante",
            "estrella",
            "flor",
            "gato",
            "hablar",
            "hoja",
            "isla",
            "jamón",
            "jirafa",
            "lago",
            "luz",
            "mamá",
            "madam",
            "neón",
            "oso",
            "osos",
            "osotes",
            "paz",
            "radar",
            "racecar",
            "reconocer",
            "sol",
            "tomate",
            "universo",
            "valle",
            "zanahoria"
    );

    //Encontrar las palabras que comienzan con las letras de la "a" a la "m".
    List<String> wordAToM() {
        return diccionario.stream()
                .filter(w -> w.charAt(0) >= 'a' && w.charAt(0) <= 'm')
                .toList();
//        return diccionario.stream()
//                .filter(w -> w.matches("^[a-m].*"))
//                .toList();
    }

    //Encontrar las palabras que comienzan con la letra "n" hasta el final del diccionario.
    List<String> wordNToFinal(){
        return diccionario.stream()
                .filter(w -> w.charAt(0) >= 'n')
                .toList();
    }
    //Agrupar las palabras del diccionario por sus tres primeras letras.
    Map<String, List<String>> agrupar3FirstLetter(){
        return diccionario.stream()
                .filter(w -> w.length() >= 3)
                .collect(Collectors.groupingBy(
                        w -> threeLetter(w)
                ));
    }
    private String threeLetter(String w) {
        return w.substring(0, 3);
    }
    //Encontrar los palíndromos en el diccionario. Un palíndromo es una palabra, número, frase u
    // otra secuencia de caracteres que se lee igual de izquierda a derecha y viceversa, como
    // "madam" o "racecar".
    List<String> getPalindromos() {
        return diccionario.stream()
                .filter(w -> darLaVuelta(w).equals(w))
                .toList();
    }
    private String darLaVuelta(String w) {
        String invertida = "";
        for (int i=w.length() - 1; i>=0; i--){
            invertida += w.charAt(i);
        }
        return invertida;
    }
    //Contar las vocales utilizadas en las palabras.
    int numVocales() {
        return diccionario.stream()
                .mapToInt(w -> vocales(w))
                .sum();
    }
    private int vocales(String w) {
        String vocals = "aeiouáéíóúü";
        int num = 0;
        for (int i=0; i<w.length(); i++){
            if (vocals.contains(String.valueOf(w.charAt(i)))){num++;}
        }
        return num;
    }
    //Encontrar las palabras que comienzan con la letra "a" y terminan con la letra "z".
    List<String> startAendZ() {
        return diccionario.stream()
                .filter(w -> (w.charAt(0) == 'a') && (w.charAt(w.length() - 1) == 'z'))
                .toList();
    }
    //Encontrar la palabra más larga en el diccionario.
    String longestWord() {
//        return diccionario.stream()
//                .sorted((w1, w2) -> Integer.compare(w2.length(), w1.length()))
//                .findFirst()
//                .orElse("");
//
        return diccionario.stream()
                .max(Comparator.comparing(String::length))
                .orElse("");
    }


}
