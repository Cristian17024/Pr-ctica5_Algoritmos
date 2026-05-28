package com.example.practica5;

import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class GraficasOrdenamiento extends BorderPane {

    // Nombres fijos para la interfaz
    private final String[] nombresAlgoritmos = {
            "QuickSort", "MergeSort", "ShellSort", "Selección Directa", "RadixSort", "Arrays.sort()", "Arrays.parallelSort()"
    };
    private final String[] nombresParametros = {
            "Título", "Rating", "Reviews", "Ranking", "Género"
    };


    private final double[][] tiemposPrecalculados = new double[7][5];

    private final CheckBox[] chkAlgoritmos = new CheckBox[7];
    private final CheckBox[] chkParametros = new CheckBox[5];
    private final GridPane gridGraficas;

    public GraficasOrdenamiento(List<VideoJuego> datosOriginales) {
        precalcularTiempos(datosOriginales);
        VBox panelSuperior = crearPanelControles();
        setTop(panelSuperior);

        gridGraficas = new GridPane();
        gridGraficas.setHgap(15);
        gridGraficas.setVgap(15);
        gridGraficas.setAlignment(Pos.CENTER);
        gridGraficas.setStyle("-fx-padding: 20px;");

        ScrollPane scroll = new ScrollPane(gridGraficas);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        setCenter(scroll);

        actualizarGraficas();
    }


    private void precalcularTiempos(List<VideoJuego> orig) {
        Comparator<VideoJuego> cTitulo = Comparator.comparing(VideoJuego::getTitulo);
        Comparator<VideoJuego> cRating = Comparator.comparingDouble(VideoJuego::getRating);
        Comparator<VideoJuego> cReviews = Comparator.comparingInt(VideoJuego::getReviews);
        Comparator<VideoJuego> cRanking = Comparator.comparingInt(VideoJuego::getRanking);
        Comparator<VideoJuego> cGenero = Comparator.comparing(VideoJuego::getGenero);

        tiemposPrecalculados[0][0] = medirQuickSort(orig, cTitulo);
        tiemposPrecalculados[0][1] = medirQuickSort(orig, cRating);
        tiemposPrecalculados[0][2] = medirQuickSort(orig, cReviews);
        tiemposPrecalculados[0][3] = medirQuickSort(orig, cRanking);
        tiemposPrecalculados[0][4] = medirQuickSort(orig, cGenero);

        tiemposPrecalculados[1][0] = medirMergeSort(orig, cTitulo);
        tiemposPrecalculados[1][1] = medirMergeSort(orig, cRating);
        tiemposPrecalculados[1][2] = medirMergeSort(orig, cReviews);
        tiemposPrecalculados[1][3] = medirMergeSort(orig, cRanking);
        tiemposPrecalculados[1][4] = medirMergeSort(orig, cGenero);

        tiemposPrecalculados[2][0] = medirShellSort(orig, cTitulo);
        tiemposPrecalculados[2][1] = medirShellSort(orig, cRating);
        tiemposPrecalculados[2][2] = medirShellSort(orig, cReviews);
        tiemposPrecalculados[2][3] = medirShellSort(orig, cRanking);
        tiemposPrecalculados[2][4] = medirShellSort(orig, cGenero);

        tiemposPrecalculados[3][0] = medirSeleccion(orig, cTitulo);
        tiemposPrecalculados[3][1] = medirSeleccion(orig, cRating);
        tiemposPrecalculados[3][2] = medirSeleccion(orig, cReviews);
        tiemposPrecalculados[3][3] = medirSeleccion(orig, cRanking);
        tiemposPrecalculados[3][4] = medirSeleccion(orig, cGenero);

        tiemposPrecalculados[4][0] = medirRadixSortTexto(orig, VideoJuego::getTitulo);
        tiemposPrecalculados[4][1] = medirRadixSortDecimal(orig, VideoJuego::getRating);
        tiemposPrecalculados[4][2] = medirRadixSortEntero(orig, VideoJuego::getReviews);
        tiemposPrecalculados[4][3] = medirRadixSortEntero(orig, VideoJuego::getRanking);
        tiemposPrecalculados[4][4] = medirRadixSortTexto(orig, VideoJuego::getGenero);

        tiemposPrecalculados[5][0] = medirJavaSort(orig, cTitulo);
        tiemposPrecalculados[5][1] = medirJavaSort(orig, cRating);
        tiemposPrecalculados[5][2] = medirJavaSort(orig, cReviews);
        tiemposPrecalculados[5][3] = medirJavaSort(orig, cRanking);
        tiemposPrecalculados[5][4] = medirJavaSort(orig, cGenero);

        tiemposPrecalculados[6][0] = medirJavaParallelSort(orig, cTitulo);
        tiemposPrecalculados[6][1] = medirJavaParallelSort(orig, cRating);
        tiemposPrecalculados[6][2] = medirJavaParallelSort(orig, cReviews);
        tiemposPrecalculados[6][3] = medirJavaParallelSort(orig, cRanking);
        tiemposPrecalculados[6][4] = medirJavaParallelSort(orig, cGenero);
    }

    private VBox crearPanelControles() {
        VBox panel = new VBox(15);
        panel.setStyle("-fx-padding: 15px; -fx-background-color: #eef2f3; -fx-border-color: #cccccc; -fx-border-width: 0 0 2 0;");
        panel.setAlignment(Pos.CENTER);


        Label lblAlgoritmos = new Label("Selecciona los Algoritmos a comparar:");
        lblAlgoritmos.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        FlowPane flowAlgoritmos = new FlowPane(15, 10);
        flowAlgoritmos.setAlignment(Pos.CENTER);
        for (int i = 0; i < nombresAlgoritmos.length; i++) {
            chkAlgoritmos[i] = new CheckBox(nombresAlgoritmos[i]);
            chkAlgoritmos[i].setSelected(true);
            chkAlgoritmos[i].setOnAction(e -> actualizarGraficas());
            flowAlgoritmos.getChildren().add(chkAlgoritmos[i]);
        }

        Label lblParametros = new Label("Selecciona los Parámetros a mostrar en las gráficas:");
        lblParametros.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        FlowPane flowParametros = new FlowPane(15, 10);
        flowParametros.setAlignment(Pos.CENTER);
        for (int i = 0; i < nombresParametros.length; i++) {
            chkParametros[i] = new CheckBox(nombresParametros[i]);
            chkParametros[i].setSelected(true);
            chkParametros[i].setOnAction(e -> actualizarGraficas());
            flowParametros.getChildren().add(chkParametros[i]);
        }

        panel.getChildren().addAll(lblAlgoritmos, flowAlgoritmos, lblParametros, flowParametros);
        return panel;
    }

    private void actualizarGraficas() {
        gridGraficas.getChildren().clear(); // Limpiamos la pantalla

        int columna = 0;
        int fila = 0;

        for (int i = 0; i < nombresAlgoritmos.length; i++) {

            if (chkAlgoritmos[i].isSelected()) {
                BarChart<String, Number> grafica = crearGraficaDinamica(i);
                gridGraficas.add(grafica, columna, fila);

                columna++;
                if (columna == 3) {
                    columna = 0;
                    fila++;
                }
            }
        }
    }

    private BarChart<String, Number> crearGraficaDinamica(int indexAlgoritmo) {
        CategoryAxis ejeX = new CategoryAxis();
        NumberAxis ejeY = new NumberAxis();
        ejeY.setLabel("Tiempo (µs)");
        ejeX.setTickLabelRotation(-45);

        BarChart<String, Number> grafica = new BarChart<>(ejeX, ejeY);
        grafica.setTitle(nombresAlgoritmos[indexAlgoritmo]);
        grafica.setLegendVisible(false);

        XYChart.Series<String, Number> serie = new XYChart.Series<>();

        for (int j = 0; j < nombresParametros.length; j++) {
            if (chkParametros[j].isSelected()) {
                double tiempoReal = tiemposPrecalculados[indexAlgoritmo][j];
                serie.getData().add(new XYChart.Data<>(nombresParametros[j], Math.max(tiempoReal, 0.1)));
            }
        }

        grafica.getData().add(serie);
        grafica.setPrefSize(400, 220);

        return grafica;
    }

    private double medirQuickSort(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        List<VideoJuego> warmup = clonarLista(orig); QuickSort.ordenar(warmup, 0, warmup.size() - 1, comp);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        QuickSort.ordenar(copia, 0, copia.size() - 1, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirMergeSort(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        List<VideoJuego> warmup = clonarLista(orig); MergeSort.ordenar(warmup, comp);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        MergeSort.ordenar(copia, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirShellSort(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        List<VideoJuego> warmup = clonarLista(orig); ShellSort.ordenar(warmup, comp);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        ShellSort.ordenar(copia, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirSeleccion(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        List<VideoJuego> warmup = clonarLista(orig); SeleccionDirecta.ordenar(warmup, comp);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        SeleccionDirecta.ordenar(copia, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirRadixSortEntero(List<VideoJuego> orig, java.util.function.ToIntFunction<VideoJuego> extractor) {
        List<VideoJuego> warmup = clonarLista(orig); RadixSort.ordenar(warmup, extractor);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        RadixSort.ordenar(copia, extractor);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirRadixSortDecimal(List<VideoJuego> orig, java.util.function.ToDoubleFunction<VideoJuego> extractor) {
        List<VideoJuego> warmup = clonarLista(orig); RadixSort.ordenarDecimales(warmup, extractor);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        RadixSort.ordenarDecimales(copia, extractor);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirRadixSortTexto(List<VideoJuego> orig, java.util.function.Function<VideoJuego, String> extractor) {
        List<VideoJuego> warmup = clonarLista(orig); RadixSort.ordenarTextos(warmup, extractor);
        List<VideoJuego> copia = clonarLista(orig);
        long inicio = System.nanoTime();
        RadixSort.ordenarTextos(copia, extractor);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirJavaSort(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        VideoJuego[] warmup = orig.toArray(new VideoJuego[0]); Arrays.sort(warmup, comp);
        VideoJuego[] arreglo = orig.toArray(new VideoJuego[0]);
        long inicio = System.nanoTime();
        Arrays.sort(arreglo, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private double medirJavaParallelSort(List<VideoJuego> orig, Comparator<VideoJuego> comp) {
        VideoJuego[] warmup = orig.toArray(new VideoJuego[0]); Arrays.parallelSort(warmup, comp);
        VideoJuego[] arreglo = orig.toArray(new VideoJuego[0]);
        long inicio = System.nanoTime();
        Arrays.parallelSort(arreglo, comp);
        return (System.nanoTime() - inicio) / 1000.0;
    }

    private List<VideoJuego> clonarLista(List<VideoJuego> original) {
        List<VideoJuego> copia = new ArrayList<>(original.size());
        for (VideoJuego v : original) copia.add(new VideoJuego(v));
        return copia;
    }
}