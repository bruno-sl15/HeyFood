package com.heyfood.heyfoodapp.prato.dominio;

import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;

import java.math.BigDecimal;

public class Prato {
    private long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Restaurante restaurante;
    private Double notaMedia;
    //TODO: FOTO

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }
}

