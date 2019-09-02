package com.heyfood.heyfoodapp.pessoa.dominio;

import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;

/**
 * Created by bruno.silvaleite on 10/05/2019.
 */

public class Pessoa {
    private long id;
    private String nome;
    private String cpf;
    private String dataNascimento;
    private Endereco endereco;
    private Contato contato;
    private TipoGenero genero;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNAscimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public TipoGenero getGenero() {
        return genero;
    }

    public void setGenero(TipoGenero genero) {
        this.genero = genero;
    }
}
