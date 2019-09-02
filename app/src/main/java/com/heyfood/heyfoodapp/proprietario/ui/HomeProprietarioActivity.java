package com.heyfood.heyfoodapp.proprietario.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.proprietario.negocio.ProprietarioServices;
import com.heyfood.heyfoodapp.restaurante.ui.CadastrarRestauranteActivity;
import com.heyfood.heyfoodapp.restaurante.ui.ListarRestaurantes;
import com.heyfood.heyfoodapp.usuario.ui.LoginActivity;

public class HomeProprietarioActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView nome;
    TextView nomeMenu;
    TextView emailMenu;
    Proprietario proprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_proprietario);
        proprietario = Sessao.instance.getProprietario();

        nome = findViewById(R.id.textBoasVindasProprietarioId);
        nome.setText(String.format("Bem vindo, %s!", proprietario.getUsuario().getPessoa().getNome()));

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
        nomeMenu = headerView.findViewById(R.id.nomeMenuProprietarioId);
        nomeMenu.setText(proprietario.getUsuario().getPessoa().getNome());
        emailMenu = headerView.findViewById(R.id.emailMenuProprieatrioId);
        emailMenu.setText(proprietario.getUsuario().getPessoa().getContato().getEmail());
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
        getMenuInflater().inflate(R.menu.home_proprietario_activity2, menu);
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
            Intent novaTela = new Intent(this, PerfilProprietarioActivity.class);
            startActivity(novaTela);
        } else if (id == R.id.cadastrarRestauranteId) {
            Intent novaTela = new Intent(this, CadastrarRestauranteActivity.class);
            startActivity(novaTela);

        } else if (id == R.id.listarRestaurantesId) {
            Intent novaTela = new Intent (this, ListarRestaurantes.class);
            startActivity(novaTela);

        } else if (id == R.id.sairId) {
            ProprietarioServices proprietarioServices = new ProprietarioServices(this);
            proprietarioServices.logout();
            Intent novaTela = new Intent(this, LoginActivity.class);
            startActivity(novaTela);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
