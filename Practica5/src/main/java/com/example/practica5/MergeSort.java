package com.example.practica5;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort {
    public static List<VideoJuego> ordenar(List<VideoJuego> lista, Comparator<VideoJuego> comparador) {
        if (lista.size() <= 1) return lista;
        int mitad = lista.size() / 2;
        List<VideoJuego> izquierda = ordenar(new ArrayList<>(lista.subList(0, mitad)), comparador);
        List<VideoJuego> derecha = ordenar(new ArrayList<>(lista.subList(mitad, lista.size())), comparador);
        return merge(izquierda, derecha, comparador);
    }

    private static List<VideoJuego> merge(List<VideoJuego> izquierda, List<VideoJuego> derecha, Comparator<VideoJuego> comp) {
        List<VideoJuego> resultado = new ArrayList<>();
        int i = 0, j = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (comp.compare(izquierda.get(i), derecha.get(j)) <= 0) {
                resultado.add(izquierda.get(i));
                i++;
            } else {
                resultado.add(derecha.get(j));
                j++;
            }
        }
        while (i < izquierda.size()) resultado.add(izquierda.get(i++));
        while (j < derecha.size()) resultado.add(derecha.get(j++));
        return resultado;
    }
}