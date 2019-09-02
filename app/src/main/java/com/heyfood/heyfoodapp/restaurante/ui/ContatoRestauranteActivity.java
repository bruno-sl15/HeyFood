package com.heyfood.heyfoodapp.restaurante.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

public class ContatoRestauranteActivity extends AppCompatActivity {
    private EditText telefone;
    private EditText email;
    private EditText site;

    Restaurante restaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato_restaurante);

        telefone = findViewById(R.id.campoNomeId);
        email = findViewById(R.id.campoEmailId);
        site = findViewById(R.id.campoSiteId);

        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));

        restaurante = Sessao.instance.getRestaurante();

        if (Sessao.instance.getContato() != null){
            Contato restaura = Sessao.instance.getContato();
            telefone.setText(restaura.getTelefone());
            email.setText(restaura.getEmail());
            site.setText(restaura.getSite());
        }
    }

    @Override
    public void onBackPressed() {
        Sessao.instance.setContato(createContato());
        super.onBackPressed();
    }

    public Contato createContato(){
        Contato contato = new Contato();
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(email.getText().toString());
        contato.setSite(site.getText().toString());

        return contato;
    }

    private boolean validarCampos(){
        return
                telefone.getText().toString().length() != 0 &&
                        email.getText().toString().length() != 0;

    }

    private boolean validarEmail(){
        String email = this.email.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public void proximaTela2(View view){
        if(!validarCampos()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (!validarEmail()){
            Toast.makeText(this, "Email inválido.", Toast.LENGTH_LONG).show();
            return;
        }
        restaurante.setContato(createContato());
        Sessao.instance.setRestaurante(restaurante);

        Intent novaTela = new Intent(this, EspecialidadeRestauranteActivity.class);
        startActivity(novaTela);
    }

}