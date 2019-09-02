package com.heyfood.heyfoodapp.restaurante.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoRestaurante;
import com.heyfood.heyfoodapp.avaliacao.persistencia.AvaliacaoRestauranteDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.prato.ui.CadastrarPratoActivity;
import com.heyfood.heyfoodapp.prato.ui.ListarPratosActivity;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.persistencia.RestauranteDAO;
import com.heyfood.heyfoodapp.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class ListarRestaurantes extends AppCompatActivity {
    private RecyclerView recyclerRestaurante;
    private RestauranteDAO restauranteDAO;
    private AvaliacaoRestauranteDAO avaliacaoRestauranteDAO;
    private Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_restaurantes);
        contexto = this;

        restauranteDAO = new RestauranteDAO(this);
        List<Restaurante> listaRestaurante;

        recyclerRestaurante = findViewById(R.id.recyclerRestaurante);

        if (Sessao.instance.getCliente() != null){
            listaRestaurante = restauranteDAO.getListaRestaurantes();
        }
        else {
            listaRestaurante = restauranteDAO.getListaMeusRestaurantes(Sessao.instance.getProprietario().getId());
        }

        AdapterRestaurante adapter = new AdapterRestaurante(listaRestaurante);
        final List<Restaurante> finalListaRestaurante = listaRestaurante;

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerRestaurante.setLayoutManager(layoutManager);
        recyclerRestaurante.setHasFixedSize(true);
        recyclerRestaurante.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerRestaurante.setAdapter( adapter );

        recyclerRestaurante.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerRestaurante,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                final Restaurante restaurante = finalListaRestaurante.get(position);

                                avaliacaoRestauranteDAO = new AvaliacaoRestauranteDAO(contexto);
                                ArrayList<AvaliacaoRestaurante> avaliacoes;
                                avaliacoes = avaliacaoRestauranteDAO.getAvaliacoes(restaurante.getId());

                                Double soma = 0.0;

                                for(AvaliacaoRestaurante avaliacao: avaliacoes){
                                    soma = soma + avaliacao.getNota();
                                }
                                Double media = soma / avaliacoes.size();

                                StringBuilder mensagem = new StringBuilder();
                                mensagem.append("Endereço: \n");
                                mensagem.append(restaurante.getEndereco().getRua() + ", " + restaurante.getEndereco().getNumero() + "\n");
                                mensagem.append(restaurante.getEndereco().getBairro() + "\n");
                                mensagem.append(restaurante.getEndereco().getCidade() + "\n\n");
                                mensagem.append("Fone: " + restaurante.getContato().getTelefone() + "\n");
                                mensagem.append("Email: " + restaurante.getContato().getEmail() + "\n");
                                mensagem.append("Média: " + media.toString().replace(".",",") + "\n");

                                mensagem.append(restaurante.getContato().getSite());

                                AlertDialog.Builder dialog = new AlertDialog.Builder(ListarRestaurantes.this);

                                //Configura botão
                                LinearLayout linearLayout = new LinearLayout(contexto);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                Button botao = new Button(contexto);
                                linearLayout.addView(botao);
                                botao.setText("Listar pratos");
                                botao.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Sessao.instance.setRestaurante(restaurante);
                                        Intent novaTela = new Intent(contexto, ListarPratosActivity.class);
                                        startActivity(novaTela);
                                    }
                                });

                                //Configura titulo e mensagem
                                dialog.setTitle(restaurante.getNome());
                                dialog.setMessage(mensagem.toString());
                                dialog.setView(linearLayout);
                                dialog.setCancelable(true);

                                if (Sessao.instance.getProprietario() != null){
                                    dialog.setNegativeButton("EDITAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Sessao.instance.setRestaurante(restaurante);
                                            Intent novaTela = new Intent(contexto, AtualizarRestauranteActivity.class);
                                            startActivity(novaTela);

                                        }
                                    });

                                    //Configura acoes para o botao
                                    dialog.setPositiveButton("CADASTRAR PRATO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Sessao.instance.setRestaurante(restaurante);
                                            Intent novaTela = new Intent(contexto, CadastrarPratoActivity.class);
                                            startActivity(novaTela);

                                        }
                                    });
                                } else{
                                    dialog.setPositiveButton("Avaliar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    final RatingBar ratingBar = new RatingBar(contexto);
                                    ratingBar.setMax(5);
                                    ratingBar.setRating(0.0f);
                                    ratingBar.setNumStars(5);
                                    if (avaliacaoRestauranteDAO.getAvaliacao(Sessao.instance.getCliente().getId(), restaurante.getId()) != null){
                                        ratingBar.setRating(avaliacaoRestauranteDAO.getAvaliacao(Sessao.instance.getCliente().getId(), restaurante.getId()).getNota());
                                    }
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                                    ratingBar.setLayoutParams(lp);
                                    linearLayout.addView(ratingBar);

                                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (avaliacaoRestauranteDAO.getAvaliacao(Sessao.instance.getCliente().getId(), restaurante.getId()) == null) {
                                                AvaliacaoRestaurante avaliacao = new AvaliacaoRestaurante();
                                                avaliacao.setRestaurante(restaurante);
                                                avaliacao.setCliente(Sessao.instance.getCliente());
                                                avaliacao.setNota(ratingBar.getRating());
                                                AvaliacaoRestauranteDAO avaliacaoRestauranteDAO = new AvaliacaoRestauranteDAO(contexto);
                                                avaliacaoRestauranteDAO.cadastrar(avaliacao);
                                            }
                                            else {
                                                AvaliacaoRestaurante avaliacao;
                                                avaliacao = avaliacaoRestauranteDAO.getAvaliacao(Sessao.instance.getCliente().getId(), restaurante.getId());
                                                avaliacao.setNota(ratingBar.getRating());
                                                AvaliacaoRestauranteDAO avaliacaoRestauranteDAO = new AvaliacaoRestauranteDAO(contexto);
                                                avaliacaoRestauranteDAO.updateNota(avaliacao);
                                            }
                                        }
                                    });
                                }

                                //Criar e exbir o alertDialog
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }
}

