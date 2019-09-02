package com.heyfood.heyfoodapp.avaliacao.dominio;

import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.prato.dominio.Prato;


public class AvaliacaoPrato {
    private long id;
    private Prato prato;
    private Cliente cliente;
    private float nota;

    public long getId() {
        return id;
    }

    public Prato getPrato() {
        return prato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public float getNota() {
        return nota;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
