package com.heyfood.heyfoodapp.cliente.dominio;

import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.usuario.dominio.Usuario;

/**
 * Created by GABRIEL.CABOCLO on 29/05/2019.
 */

public class Cliente {
    private long id;
    private Usuario usuario;
    private Categoria preferencias;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Categoria preferencias) {
        this.preferencias = preferencias;
    }
}
