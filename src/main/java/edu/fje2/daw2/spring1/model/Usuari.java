package edu.fje2.daw2.spring1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

public class Usuari {
    @Id
    private String username;

    private String mail;

    private List<Ciutat> ciutats;

    public Usuari() {
    }

    public Usuari(String username, String mail, List<Ciutat> ciutats) {
        this.username = username;
        this.mail = mail;
        this.ciutats = ciutats;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Ciutat> getCiutats() {
        return ciutats;
    }

    public void setCiutats(List<Ciutat> ciutats) {
        this.ciutats = ciutats;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", ciutats=" + ciutats +
                '}';
    }
}
