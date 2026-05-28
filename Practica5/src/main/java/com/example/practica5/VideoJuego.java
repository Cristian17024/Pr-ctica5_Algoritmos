package com.example.practica5;

public class VideoJuego {
    private String titulo;
    private double rating;
    private int reviews;
    private int ranking;
    private String genero;

    public VideoJuego(String titulo, double rating, int reviews, int ranking, String genero) {
        this.titulo = titulo;
        this.rating = rating;
        this.reviews = reviews;
        this.ranking = ranking;
        this.genero = genero;
    }

    public VideoJuego(VideoJuego otro) {
        this.titulo = otro.titulo;
        this.rating = otro.rating;
        this.reviews = otro.reviews;
        this.ranking = otro.ranking;
        this.genero = otro.genero;
    }

    public String getTitulo() { return titulo; }
    public double getRating() { return rating; }
    public int getReviews() { return reviews; }
    public int getRanking() { return ranking; }
    public String getGenero() { return genero; }
}