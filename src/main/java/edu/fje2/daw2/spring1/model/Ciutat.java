package edu.fje2.daw2.spring1.model;

public class Ciutat {
    private String nom;
    private double latitud;
    private double longitud;

    public Ciutat() {
    }

    public Ciutat(String nom, double latitud, double longitud) {
        this.nom = nom;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
