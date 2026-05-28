package com.example.practica5;
import java.util.Comparator;
import java.util.List;

public class ShellSort {
    public static void ordenar(List<VideoJuego> lista, Comparator<VideoJuego> comparador) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                VideoJuego temp = lista.get(i);
                int j;
                for (j = i; j >= gap && comparador.compare(lista.get(j - gap), temp) > 0; j -= gap) {
                    lista.set(j, lista.get(j - gap));
                }
                lista.set(j, temp);
            }
        }
    }
}