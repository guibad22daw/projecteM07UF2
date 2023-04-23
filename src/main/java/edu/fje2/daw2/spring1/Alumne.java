package edu.fje2.daw2.spring1;

import java.util.Comparator;
import java.util.Objects;

public class Alumne {
    private String nom;
    private String cognom;
    private int nota;

    public Alumne(String nom, String cognom, int nota) {
        this.nom = nom;
        this.cognom = cognom;
        this.nota = nota;
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

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Alumne{" +
                "nom='" + nom + '\'' +
                ", cognom='" + cognom + '\'' +
                ", nota=" + nota +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumne alumne = (Alumne) o;
        return cognom.equals(alumne.cognom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cognom);
    }

}
