package edu.fje.daw2.projecteM07UF2.model;

public class Alumne {
    private String nom;
    private String cognom;
    private double nota;
    private String dataNaixement;

    public Alumne(String nom, String cognom, double nota, String dataNaixement) {
        this.nom = nom;
        this.cognom = cognom;
        this.nota = nota;
        this.dataNaixement = dataNaixement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getDataNaixement() {
        return dataNaixement;
    }

    public void setNota(String dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    @Override
    public String toString() {
        return "Alumne{" +
                "nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", nota=" + nota +
                '}';
    }
}
