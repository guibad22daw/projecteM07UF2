package edu.fje2.daw2.spring1.model;

import java.util.List;

public class Usuari {

    private String mail;
    private String id;
    private List<Ciutat> ciutats;

    public Usuari() {
    }

    public Usuari(String mail, String id, List<Ciutat> ciutats) {
        this.mail = mail;
        this.id = id;
        this.ciutats = ciutats;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Ciutat> getCiutats() {
        return ciutats;
    }

    public void setCiutats(List<Ciutat> ciutats) {
        this.ciutats = ciutats;
    }
}
