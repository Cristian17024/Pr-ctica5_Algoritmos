package com.example.practica5;
import java.util.Comparator;
import java.util.List;

public class QuickSort {
    public static void ordenar(List<VideoJuego> lista, int inicio, int fin, Comparator<VideoJuego> comparador) {
        if (inicio < fin) {
            int pivote = particion(lista, inicio, fin, comparador);
            ordenar(lista, inicio, pivote - 1, comparador);
            ordenar(lista, pivote + 1, fin, comparador);
        }
    }

    private static int particion(List<VideoJuego> lista, int inicio, int fin, Comparator<VideoJuego> comparador) {
        VideoJuego pivote = lista.get(fin);
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {

            if (comparador.compare(lista.get(j), pivote) < 0) {
                i++;
                VideoJuego temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        VideoJuego temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(fin));
        lista.set(fin, temp);
        return i + 1;
    }
}