package com.example.practica5;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public class RadixSort {

    public static void ordenar(List<VideoJuego> lista, ToIntFunction<VideoJuego> extractor) {
        if (lista == null || lista.isEmpty()) return;
        int max = obtenerMax(lista, extractor);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(lista, exp, extractor);
        }
    }

    private static int obtenerMax(List<VideoJuego> lista, ToIntFunction<VideoJuego> extractor) {
        int max = extractor.applyAsInt(lista.get(0));
        for (VideoJuego v : lista) {
            int valor = extractor.applyAsInt(v);
            if (valor > max) max = valor;
        }
        return max;
    }

    private static void countingSort(List<VideoJuego> lista, int exp, ToIntFunction<VideoJuego> extractor) {
        int n = lista.size();
        VideoJuego[] output = new VideoJuego[n];
        int[] count = new int[10];

        for (VideoJuego v : lista) { count[(extractor.applyAsInt(v) / exp) % 10]++; }
        for (int i = 1; i < 10; i++) { count[i] += count[i - 1]; }
        for (int i = n - 1; i >= 0; i--) {
            int valor = extractor.applyAsInt(lista.get(i));
            int index = (valor / exp) % 10;
            output[count[index] - 1] = lista.get(i);
            count[index]--;
        }
        for (int i = 0; i < n; i++) { lista.set(i, output[i]); }
    }

    public static void ordenarDecimales(List<VideoJuego> lista, ToDoubleFunction<VideoJuego> extractor) {
        if (lista == null || lista.isEmpty()) return;

        ToIntFunction<VideoJuego> conversor = v -> (int) (extractor.applyAsDouble(v) * 100);
        ordenar(lista, conversor);
    }

    public static void ordenarTextos(List<VideoJuego> lista, Function<VideoJuego, String> extractor) {
        if (lista == null || lista.isEmpty()) return;
        int maxLen = 0;

        for (VideoJuego v : lista) {
            String texto = extractor.apply(v);
            if (texto != null && texto.length() > maxLen) maxLen = texto.length();
        }

        for (int pos = maxLen - 1; pos >= 0; pos--) {
            countingSortTextos(lista, pos, extractor);
        }
    }

    private static void countingSortTextos(List<VideoJuego> lista, int pos, Function<VideoJuego, String> extractor) {
        int n = lista.size();
        VideoJuego[] output = new VideoJuego[n];
        int[] count = new int[65536];
        for (VideoJuego v : lista) {
            String texto = extractor.apply(v);
            int charIndex = (texto != null && pos < texto.length()) ? texto.charAt(pos) : 0;
            count[charIndex]++;
        }
        for (int i = 1; i < 65536; i++) { count[i] += count[i - 1]; }
        for (int i = n - 1; i >= 0; i--) {
            String texto = extractor.apply(lista.get(i));
            int charIndex = (texto != null && pos < texto.length()) ? texto.charAt(pos) : 0;
            output[count[charIndex] - 1] = lista.get(i);
            count[charIndex]--;
        }
        for (int i = 0; i < n; i++) { lista.set(i, output[i]); }
    }
}