package edu.fje2.daw2.spring1.model;

import java.util.List;

public class Usuari {

    private String username;

    private String mail;

    private List<Ciutat> ciutats;

    public Usuari() {
    }

    public Usuari(String mail, String username, List<Ciutat> ciutats) {
        this.mail = mail;
        this.username = username;
        this.ciutats = ciutats;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Ciutat> getCiutats() {
        return ciutats;
    }

    public void setCiutats(List<Ciutat> ciutats) {
        this.ciutats = ciutats;
    }
}
