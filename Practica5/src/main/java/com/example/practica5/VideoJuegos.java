package com.example.practica5;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class VideoJuegos {

    public static List<VideoJuego> cargarDatos() {
        List<VideoJuego> lista = new ArrayList<>();
        try {
            InputStream input = VideoJuegos.class.getResourceAsStream("/games.csv");

            if (input == null) {
                System.out.println("No se encontró el archivo games.csv");
                return lista;
            } else {
                System.out.println(" Archivo csv abierto");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            br.readLine();

            String linea;
            int numeroFila = 2;

            while ((linea = br.readLine()) != null) {
                String[] columnas = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (columnas.length >= 8) {
                    try {
                        String titulo = columnas[1].replace("\"", "").trim();
                        double rating = Double.parseDouble(columnas[4].trim());
                        int reviews = Integer.parseInt(columnas[5].trim());
                        int ranking = Integer.parseInt(columnas[6].trim());

                        String genero = columnas[7].replace("\"", "").replace("[", "").replace("]", "").replace("'", "").trim();

                        lista.add(new VideoJuego(titulo, rating, reviews, ranking, genero));

                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error de formato en fila " + numeroFila + ": " + e.getMessage());
                    }
                }
                numeroFila++;
            }
            br.close();

            System.out.println("Se cargaron " + lista.size() + " videojuegos correctamente a la lista.");

        } catch (Exception e) {
            System.out.println(" Error al leer archivo: " + e.getMessage());
        }
        return lista;
    }
}