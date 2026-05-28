package com.example.practica5;

import java.util.Arrays;
import java.util.Comparator;

public class JavaSort {

    public static void usarSort(VideoJuego[] juegos) {

        Arrays.sort(juegos,
                Comparator.comparing(VideoJuego::getTitulo));
    }

    public static void usarParallelSort(VideoJuego[] juegos) {

        Arrays.parallelSort(juegos,
                Comparator.comparing(VideoJuego::getTitulo));
    }
}