package com.heyfood.heyfoodapp.cliente.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoRestaurante;
import com.heyfood.heyfoodapp.avaliacao.persistencia.AvaliacaoRestauranteDAO;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.negocio.ClienteServices;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.prato.ui.CadastrarPratoActivity;
import com.heyfood.heyfoodapp.prato.ui.ListarPratosActivity;
import com.heyfood.heyfoodapp.recomendacao.Recomendacao;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.ui.AdapterRestaurante;
import com.heyfood.heyfoodapp.restaurante.ui.AtualizarRestauranteActivity;
import com.heyfood.heyfoodapp.restaurante.ui.ListarRestaurantes;
import com.heyfood.heyfoodapp.usuario.ui.LoginActivity;
import com.heyfood.heyfoodapp.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nome;
    TextView mensagem;
    TextView nomeMenu;
    TextView emailMenu;
    Cliente cliente;
    RecyclerView recyclerView;
    private AvaliacaoRestauranteDAO avaliacaoRestauranteDAO;
    private Recomendacao recomendacao;
    private List<Restaurante> restaurantesRecomendados;
    public static Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);
        contexto = this;
        recomendacao = new Recomendacao();
        restaurantesRecomendados = recomendacao.getListaRestaurantesRecomendados();

        cliente = Sessao.instance.getCliente();

        //Bem vindo 'nome do usuario'
        nome = findViewById(R.id.textBoasVindasClienteId);
        nome.setText(String.format("Bem vindo, %s!", cliente.getUsuario().getPessoa().getNome()));
        mensagem = findViewById(R.id.textRecomendacoes);
        recyclerView = findViewById(R.id.recyclerViewHome);

        if(restaurantesRecomendados.size()==0){
            mensagem.setText("Infelizmente ainda não temos sugestões para você, tente avaliar mais restaurantes.");
        }
        else{
            mensagem.setText("Essas são nossas sugestões para você:");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nomeMenu = headerView.findViewById(R.id.nomeMenuId);
        nomeMenu.setText(cliente.getUsuario().getPessoa().getNome());
        emailMenu = headerView.findViewById(R.id.emailMenuId);
        emailMenu.setText(cliente.getUsuario().getPessoa().getContato().getEmail());

        AdapterRestaurante adapter = new AdapterRestaurante(recomendacao.getListaRestaurantesRecomendados());
        final List<Restaurante> finalListaRestaurante = recomendacao.getListaRestaurantesRecomendados();

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapter );

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
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
                                mensagem.append("Média: " + media.toString() + "\n");

                                mensagem.append(restaurante.getContato().getSite());

                                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeClienteActivity.this);

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
                                                AvaliacaoRestaurante avaliacao = new AvaliacaoRestaurante();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Sessao.instance.reset();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cliente_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfilId) {
            Intent novaTela = new Intent(this, PerfilClienteActivity.class);
            startActivity(novaTela);
        } else if (id == R.id.categoriaId) {
            Intent novaTela = new Intent(this, PreferenciaClienteActivity.class);
            startActivity(novaTela);

        } else if (id == R.id.listarRestauranteId) {
            Intent novaTela = new Intent (this, ListarRestaurantes.class);
            startActivity(novaTela);

        } else if (id == R.id.sairId) {
            ClienteServices clienteServices = new ClienteServices(this);
            clienteServices.logout();
            Intent novaTela = new Intent(this, LoginActivity.class);
            startActivity(novaTela);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
