package com.heyfood.heyfoodapp.proprietario.dominio;

import com.heyfood.heyfoodapp.usuario.dominio.Usuario;

public class Proprietario {
    private long id;
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
