package com.heyfood.heyfoodapp.avaliacao.dominio;

import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;

public class AvaliacaoRestaurante {
    private long id;
    private Restaurante restaurante;
    private Cliente cliente;
    private float nota;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
