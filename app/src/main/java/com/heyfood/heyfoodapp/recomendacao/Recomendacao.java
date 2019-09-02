package com.heyfood.heyfoodapp.recomendacao;

import android.content.Context;
import android.support.annotation.NonNull;

import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoRestaurante;
import com.heyfood.heyfoodapp.avaliacao.persistencia.AvaliacaoRestauranteDAO;
import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.persistencia.ClienteDAO;
import com.heyfood.heyfoodapp.cliente.ui.HomeClienteActivity;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.prato.ui.ListarPratosActivity;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.persistencia.RestauranteDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Recomendacao {
    private ClienteDAO clienteDAO = new ClienteDAO(HomeClienteActivity.contexto);
    private RestauranteDAO restauranteDAO = new RestauranteDAO(HomeClienteActivity.contexto);
    private AvaliacaoRestauranteDAO avaliacaoRestauranteDAO = new AvaliacaoRestauranteDAO(HomeClienteActivity.contexto);
    private Map<Cliente,HashMap<Restaurante,Float>> usersMatrix;
    private List<Restaurante> listaRestaurantes;
    private List<Restaurante> listaRestaurantesRecomendados;
    private Map<Cliente, HashMap<Restaurante, Float>> predicao;

    public Recomendacao(){
        listaRestaurantes = restauranteDAO.getListaRestaurantes();
        usersMatrix = criaMatrizCliente();
        predicao = SlopeOne.slopeOne(usersMatrix, listaRestaurantes);
        listaRestaurantesRecomendados = getOrderList(getRecomendacao());
    }

    public Map<Cliente,HashMap<Restaurante,Float>> criaMatrizCliente(){
        Map<Cliente,HashMap<Restaurante,Float>> matrizClientes = new HashMap<>();
        List<Cliente> listaClientes = clienteDAO.getListaClientes();
        for(Cliente cliente: listaClientes){
            matrizClientes.put(cliente, criaMatrizRestaurante(cliente.getId()));
        }
        return matrizClientes;
    }

    private HashMap<Restaurante,Float> criaMatrizRestaurante(long idCliente){
        HashMap<Restaurante,Float> matrizRestaurante = new HashMap<>();
        for(Restaurante restaurante: listaRestaurantes){
            Float avaliacaoRestaurante = avaliacaoRestauranteDAO.getValorAvaliacao(idCliente, restaurante.getId());
            if(avaliacaoRestaurante != -1.0f){
                matrizRestaurante.put(restaurante, avaliacaoRestaurante);
            }
        }
        return matrizRestaurante;
    }

    private List<Avaliacao> getRecomendacao(){
        List<Avaliacao> notasCliente = new ArrayList<>();
        Cliente cliente = findCliente();
        HashMap<Restaurante, Float> avaliacoes = predicao.get(cliente);
        for(Map.Entry r: avaliacoes.entrySet()){
            Restaurante restaurante = (Restaurante) r.getKey();
            Float avaliacao = avaliacaoRestauranteDAO.getValorAvaliacao(cliente.getId(), restaurante.getId());
            Float nota = (Float)r.getValue();
            if (avaliacao == -1.0f){
                notasCliente.add(new Avaliacao(restaurante, nota));
            }
        }
        return notasCliente;
    }

    private Cliente findCliente(){
        Cliente clienteLogado = Sessao.instance.getCliente();
        for(Map.Entry e: predicao.entrySet()){
            Cliente cliente = (Cliente) e.getKey();
            if(clienteLogado.getId() == cliente.getId()){
                return (Cliente) e.getKey();
            }
        }
        return null;
    }

    private List<Restaurante> getOrderList(List<Avaliacao> avaliacao){
        Collections.sort(avaliacao);
        List<Restaurante> restaurantes = new ArrayList<>();
        for(Avaliacao a: avaliacao){
            if(a.nota >= 2.0f){
                restaurantes.add(a.getRestaurante());
            }
        }
        return restaurantes;
    }

    public List<Restaurante> getListaRestaurantesRecomendados() {
        return this.listaRestaurantesRecomendados;
    }

    private class Avaliacao implements Comparable<Avaliacao>{
        private Restaurante restaurante;
        private Float nota;

        public Avaliacao(Restaurante restaurante, Float nota) {
            this.restaurante = restaurante;
            this.nota = nota;
        }

        public Restaurante getRestaurante() {
            return restaurante;
        }

        public void setRestaurante(Restaurante restaurante) {
            this.restaurante = restaurante;
        }

        public int compareTo(Avaliacao outra) {
            if(this.nota < outra.nota){
                return 1;
            }
            if(this.nota > outra.nota){
                return -1;
            }
            return 0;
        }
    }
}
