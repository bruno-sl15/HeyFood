package com.heyfood.heyfoodapp.contato.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.ui.PerfilClienteActivity;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.contato.persistencia.ContatoDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.proprietario.ui.PerfilProprietarioActivity;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

public class AtualizarContatoActivity extends AppCompatActivity {
    private Cliente cliente;
    private Proprietario proprietario;
    private Contato contato;
    private TextInputEditText telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_contato);
        cliente = Sessao.instance.getCliente();
        proprietario = Sessao.instance.getProprietario();
        telefone = findViewById(R.id.campoTelefoneId);
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        if(cliente!=null){
            contato = cliente.getUsuario().getPessoa().getContato();
        }
        else{
            contato = proprietario.getUsuario().getPessoa().getContato();
        }
        telefone.setText(contato.getTelefone());
    }

    private void updateContato(){
        contato.setTelefone(telefone.getText().toString());
    }

    private boolean validarCampos(){
        return telefone.length()!=0;
    }

    public void atualizar(View view){
        if(!validarCampos()){
            telefone.setError("Insira o telefone");
            return;
        }
        ContatoDAO contatoDAO = new ContatoDAO(this);
        updateContato();
        contatoDAO.updateContato(contato);
        Toast.makeText(this,"Contato alterado com sucesso!", Toast.LENGTH_SHORT).show();
        Intent novaTela;
        if(cliente!=null){
            novaTela = new Intent(this, PerfilClienteActivity.class);
        }
        else{
            novaTela = new Intent(this, PerfilProprietarioActivity.class);
        }
        startActivity(novaTela);
    }

}
