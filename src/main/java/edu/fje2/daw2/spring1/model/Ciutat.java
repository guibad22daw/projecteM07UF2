package edu.fje2.daw2.spring1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Ciutat {
    private String nom;

    private Double latitud;

    private Double longitud;

    /**
     * Constructor per defecte de la classe Ciutat.
     */
    public Ciutat() {
    }

    /**
     * Constructor amb paràmetres de la classe Ciutat.
     * @param nom El nom de la ciutat.
     * @param latitud La latitud de la ciutat.
     * @param longitud La longitud de la ciutat.
     */
    public Ciutat(String nom, Double latitud, Double longitud) {
        this.nom = nom;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    /**
     * Obté el nom de la ciutat.
     * @return El nom de la ciutat.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de la ciutat.
     * @param nom El nom de la ciutat.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la latitud de la ciutat.
     * @return La latitud de la ciutat.
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Estableix la latitud de la ciutat.
     * @param latitud La latitud de la ciutat.
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * Obté la longitud de la ciutat.
     * @return La longitud de la ciutat.
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Estableix la longitud de la ciutat.
     * @param longitud La longitud de la ciutat.
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * Obté una representació JSON de la instància actual de la classe Ciutat.
     * @return Una cadena de caràcters que representa la instància actual de la classe Ciutat en format JSON.
     */
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
