package com.heyfood.heyfoodapp.contato.dominio;

public class Contato {
    private long id;
    private String telefone;
    private String email;
    private String site;

    public long getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getSite() {
        return site;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
