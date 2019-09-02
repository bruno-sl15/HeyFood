package com.heyfood.heyfoodapp.infra;

import android.annotation.SuppressLint;

import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.pessoa.dominio.Pessoa;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.usuario.dominio.Usuario;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Sessao {
    public static final Sessao instance = new Sessao();
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private final Map<String,Object> values = new HashMap<>();

    public void setUsuario(Usuario usuario) {
        setValue("sessao.Usuario", usuario);
    }

    public Usuario getUsuario() {

        return (Usuario)values.get("sessao.Usuario");
    }

    public void setCliente(Cliente cliente) {
        setValue("sessao.Cliente", cliente);
    }

    public Cliente getCliente() {
        return (Cliente)values.get("sessao.Cliente");
    }

    public void setProprietario(Proprietario proprietario){
        setValue("sessao.Proprietario", proprietario);
    }

    public Proprietario getProprietario() {
        return (Proprietario) values.get("sessao.Proprietario");
    }


    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);

    }

    public Object getValue(String key) {
        return values.get(key);
    }

    public void reset() {
        this.values.clear();
    }

    public Restaurante getRestaurante(){
        return (Restaurante) values.get("sessao.Restaurante");
    }

    public Contato getContato(){
        return (Contato) values.get("sessao.Contato");
    }

    public void setContato(Contato contato){
        setValue("sessao.Contato", contato);
    }

    public void setRestaurante(Restaurante restaurante) {
        setValue("sessao.Restaurante", restaurante);
    }
    public Pessoa getPessoa(){
        return (Pessoa) values.get("sessao.Pessoa");
    }

    public void setPessoa(Pessoa pessoa){
        setValue("sessao.Pessoa", pessoa);
    }

}