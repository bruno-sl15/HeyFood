package com.heyfood.heyfoodapp.prato.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.prato.dominio.Prato;
import com.heyfood.heyfoodapp.prato.persistencia.PratoDAO;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.ui.ListarRestaurantes;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

import java.math.BigDecimal;

public class
CadastrarPratoActivity extends AppCompatActivity {

    Restaurante restaurante = Sessao.instance.getRestaurante();
    EditText nomePrato, descricao, preco;
    PratoDAO pratoDAO = new PratoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_prato);

        nomePrato = findViewById(R.id.nomePratoId);
        descricao = findViewById(R.id.descricaoId);
        preco = findViewById(R.id.precoId);
        

    }

    public void cadastrarPrato(View view){
        Prato prato = createPrato();
        long id = pratoDAO.cadastrar(prato);
        prato.setId(id);
        Intent novaTela = new Intent(this, ListarRestaurantes.class);
        startActivity(novaTela);
        Toast.makeText(this, "Prato cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    private Prato createPrato(){
        Prato prato = new Prato();
        prato.setRestaurante(restaurante);
        prato.setNome(nomePrato.getText().toString());
        prato.setDescricao(descricao.getText().toString());
        prato.setPreco(BigDecimal.valueOf(Double.parseDouble(preco.getText().toString())));

        return prato;
    }

}
