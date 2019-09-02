package com.heyfood.heyfoodapp.restaurante.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.ui.HomeProprietarioActivity;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.negocio.RestauranteServices;


public class EspecialidadeRestauranteActivity extends AppCompatActivity {
    Restaurante restaurante;

    Switch acai;
    Switch brasileira;
    Switch carnes;
    Switch chinesa;
    Switch conteporanea;
    Switch italiana;
    Switch japonesa;
    Switch lanches;
    Switch marmita;
    Switch pizza;
    Switch saudavel;
    Switch alacarte;
    Switch rodizio;
    Switch selfservice;
    Switch delivery;

    private final RestauranteServices services = new RestauranteServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidade_restaurante);

        acai = findViewById(R.id.restauranteAcaiId);
        brasileira = findViewById(R.id.restauranteBrasileiraId);
        carnes = findViewById(R.id.restauranteCarnesId);
        chinesa = findViewById(R.id.restauranteChinesaId);
        conteporanea = findViewById(R.id.restauranteConteporanea);
        italiana = findViewById(R.id.restauranteItalianaId);
        japonesa = findViewById(R.id.restauranteJaponesaId);
        lanches = findViewById(R.id.restauranteLanchesId);
        marmita = findViewById(R.id.restauranteMarmitaId);
        pizza = findViewById(R.id.restaurantePizzaId);
        saudavel = findViewById(R.id.restauranteSaudavelId);
        alacarte = findViewById(R.id.restauranteAlacarteId);
        rodizio = findViewById(R.id.restauranteRodizioId);
        selfservice = findViewById(R.id.restauranteSelfserviceId);
        delivery = findViewById(R.id.restauranteDeliveryId);

        restaurante = Sessao.instance.getRestaurante();
    }


    public Categoria createCategoria(){
        Categoria categoria = new Categoria();
        if(acai.isChecked()){
            categoria.setAcai(true);
        }
        else{
            categoria.setAcai(false);

        }
        if(brasileira.isChecked()){
            categoria.setBrasileira(true);
        }
        else{
            categoria.setBrasileira(false);

        }
        if(carnes.isChecked()){
            categoria.setCarnes(true);
        }
        else{
            categoria.setCarnes(false);
        }
        if(chinesa.isChecked()){
            categoria.setChinesa(true);
        }
        else{
            categoria.setChinesa(false);
        }
        if(conteporanea.isChecked()){
            categoria.setContemporanea(true);
        }
        else{
            categoria.setContemporanea(false);
        }
        if(italiana.isChecked()){
            categoria.setItaliana(true);
        }
        else{
            categoria.setItaliana(false);
        }
        if(japonesa.isChecked()){
            categoria.setJaponesa(true);
        }
        else{
            categoria.setJaponesa(false);
        }
        if(lanches.isChecked()){
            categoria.setLanches(true);
        }
        else{
            categoria.setLanches(false);
        }
        if(marmita.isChecked()){
            categoria.setMarmita(true);
        }
        else{
            categoria.setMarmita(false);
        }
        if(pizza.isChecked()){
            categoria.setPizza(true);
        }
        else{
            categoria.setPizza(false);
        }
        if(saudavel.isChecked()){
            categoria.setSaudavel(true);
        }
        else{
            categoria.setSaudavel(false);
        }
        if(alacarte.isChecked()){
            categoria.setAlacarte(true);
        }
        else{
            categoria.setAlacarte(false);
        }
        if(rodizio.isChecked()){
            categoria.setRodizio(true);
        }
        else{
            categoria.setRodizio(false);

        }
        if(selfservice.isChecked()){
            categoria.setSelfservice(true);
        }
        else{
            categoria.setSelfservice(false);
        }
        if(delivery.isChecked()){
            categoria.setDelivery(true);
        }
        else{
            categoria.setDelivery(false);
        }

        return categoria;
    }


    public void finalizarCadastroRestaurante(View view){
        try{
            restaurante.setEspecialidades(createCategoria());
            restaurante.setProprietario(Sessao.instance.getProprietario());
            Sessao.instance.setRestaurante(restaurante);
            services.cadastrar(restaurante);

            Intent novaTela = new Intent(this, HomeProprietarioActivity.class);
            startActivity(novaTela);
            Toast.makeText(this, "Restaurante cadastrado com sucesso", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Erro ao cadastrar restaurante", Toast.LENGTH_LONG).show();
        }

    }
}
