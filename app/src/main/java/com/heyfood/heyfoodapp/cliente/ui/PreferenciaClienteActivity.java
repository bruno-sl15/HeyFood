package com.heyfood.heyfoodapp.cliente.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.cliente.negocio.ClienteServices;
import com.heyfood.heyfoodapp.infra.Sessao;


public class PreferenciaClienteActivity extends AppCompatActivity {

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
    Categoria preferencias;

    private final ClienteServices services = new ClienteServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencia_cliente);

        acai = findViewById(R.id.clienteAcaiId);
        brasileira = findViewById(R.id.clienteBrasileiraId);
        carnes = findViewById(R.id.clienteCarnesId);
        chinesa = findViewById(R.id.clienteChinesaId);
        conteporanea = findViewById(R.id.clienteContemporaneaId);
        italiana = findViewById(R.id.clienteItalianaId);
        japonesa = findViewById(R.id.clienteJaponesaId);
        lanches = findViewById(R.id.clienteLanchesId);
        marmita = findViewById(R.id.clienteMarmitaId);
        pizza = findViewById(R.id.clientePizzaId);
        saudavel = findViewById(R.id.clienteSaudavelId);
        alacarte = findViewById(R.id.clienteAlacarteId);
        rodizio = findViewById(R.id.clienteRodizioId);
        selfservice = findViewById(R.id.clienteSelfserviceId);
        delivery = findViewById(R.id.clienteDeliveryId);
        preferencias = Sessao.instance.getCliente().getPreferencias();
        if(preferencias != null){
            recuperarCategoria();
        }
    }

    private void recuperarCategoria(){
        acai.setChecked(preferencias.getAcai());
        brasileira.setChecked(preferencias.getBrasileira());
        carnes.setChecked(preferencias.getCarnes());
        chinesa.setChecked(preferencias.getChinesa());
        conteporanea.setChecked(preferencias.getContemporanea());
        italiana.setChecked(preferencias.getItaliana());
        japonesa.setChecked(preferencias.getJaponesa());
        lanches.setChecked(preferencias.getLanches());
        marmita.setChecked(preferencias.getMarmita());
        pizza.setChecked(preferencias.getPizza());
        saudavel.setChecked(preferencias.getSaudavel());
        alacarte.setChecked(preferencias.getAlacarte());
        rodizio.setChecked(preferencias.getRodizio());
        selfservice.setChecked(preferencias.getSelfservice());
        delivery.setChecked(preferencias.getDelivery());
    }

    private Categoria createCategoria(){
        Categoria categoria = new Categoria();
        categoria.setAcai(acai.isChecked());
        categoria.setBrasileira(brasileira.isChecked());
        categoria.setCarnes(carnes.isChecked());
        categoria.setChinesa(chinesa.isChecked());
        categoria.setContemporanea(conteporanea.isChecked());
        categoria.setItaliana(italiana.isChecked());
        categoria.setJaponesa(japonesa.isChecked());
        categoria.setLanches(lanches.isChecked());
        categoria.setMarmita(marmita.isChecked());
        categoria.setPizza(pizza.isChecked());
        categoria.setSaudavel(saudavel.isChecked());
        categoria.setAlacarte(alacarte.isChecked());
        categoria.setRodizio(rodizio.isChecked());
        categoria.setSelfservice(selfservice.isChecked());
        categoria.setDelivery(delivery.isChecked());
        return categoria;
    }


    public void finalizarCadastroPreferencias(View view){
        try{
            Categoria preferencias = createCategoria();
            services.cadastrarPreferencias(preferencias);

            Intent novaTela = new Intent(this, HomeClienteActivity.class);
            startActivity(novaTela);
            Toast.makeText(this, "Preferencias cadastrado com sucesso", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Erro ao cadastrar preferencias", Toast.LENGTH_LONG).show();
        }

    }
}
