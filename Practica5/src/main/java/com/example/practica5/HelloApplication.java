package com.example.practica5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.List;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        List<VideoJuego> datos = VideoJuegos.cargarDatos();

        GraficasOrdenamiento root = new GraficasOrdenamiento(datos);

        Scene scene = new Scene(root, 1200, 900);

        stage.setTitle("Rendimiento de Métodos de Ordenamiento");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}