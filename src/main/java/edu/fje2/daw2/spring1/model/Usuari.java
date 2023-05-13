package edu.fje2.daw2.spring1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class Usuari {
    @Id
    private String username;

    private String mail;

    private List<Ciutat> ciutats;

    /**
     * Constructor per defecte sense arguments.
     */
    public Usuari() {
    }

    /**
     * Constructor amb paràmetres per inicialitzar els atributs de la classe Usuari.
     * @param username el nom d'usuari
     * @param mail el correu electrònic
     * @param ciutats la llista de ciutats preferides
     */
    public Usuari(String username, String mail, List<Ciutat> ciutats) {
        this.username = username;
        this.mail = mail;
        this.ciutats = ciutats;
    }

    /**
     * Mètode que retorna el nom d'usuari.
     * @return el nom d'usuari
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mètode que estableix el nom d'usuari.
     * @param username el nom d'usuari
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Mètode que retorna el correu electrònic.
     * @return el correu electrònic
     */
    public String getMail() {
        return mail;
    }

    /**
     * Mètode que estableix el correu electrònic.
     * @param mail el correu electrònic
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Mètode que retorna la llista de ciutats preferides.
     * @return la llista de ciutats preferides
     */
    public List<Ciutat> getCiutats() {
        return ciutats;
    }

    /**
     * Mètode que estableix la llista de ciutats preferides.
     * @param ciutats la llista de ciutats preferides
     */
    public void setCiutats(List<Ciutat> ciutats) {
        this.ciutats = ciutats;
    }

    /**
     * Mètode que retorna una representació en forma de cadena de caràcters de l'objecte Usuari,
     * que conté els seus atributs.
     * @return una cadena de caràcters amb els atributs de l'objecte Usuari
     */
    @Override
    public String toString() {
        return "Usuari{" +
                "username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", ciutats=" + ciutats +
                '}';
    }
}
