package com.example.practica5;
import java.util.Comparator;
import java.util.List;

public class SeleccionDirecta {
    public static void ordenar(List<VideoJuego> lista, Comparator<VideoJuego> comparador) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < n; j++) {
                if (comparador.compare(lista.get(j), lista.get(minimo)) < 0) {
                    minimo = j;
                }
            }
            VideoJuego temp = lista.get(minimo);
            lista.set(minimo, lista.get(i));
            lista.set(i, temp);
        }
    }
}