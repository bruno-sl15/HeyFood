package com.heyfood.heyfoodapp.usuario.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.ui.PerfilClienteActivity;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.proprietario.ui.PerfilProprietarioActivity;
import com.heyfood.heyfoodapp.usuario.dominio.Usuario;
import com.heyfood.heyfoodapp.usuario.negocio.UsuarioServices;

public class TrocarSenhaActivity extends AppCompatActivity {
    private Cliente cliente;
    private Proprietario proprietario;
    private Usuario usuario;
    private TextInputEditText senhaAtual;
    private TextInputEditText senhaNova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trocar_senha);
        cliente = Sessao.instance.getCliente();
        proprietario = Sessao.instance.getProprietario();
        senhaAtual = findViewById(R.id.campoSenhaAtualId);
        senhaNova = findViewById(R.id.campoSenhaNovaId);
        if(cliente!=null){
            usuario = cliente.getUsuario();
        }
        else{
            usuario = proprietario.getUsuario();
        }
    }

    private boolean validarCampos(){
        return senhaAtual.length()!=0 &&
                senhaNova.length()!=0;
    }

    private void updateSenha(){

    }

    public void trocarSenha(View view){
        if(!validarCampos()){
            Toast.makeText(this,"Preencha os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            UsuarioServices services = new UsuarioServices(this);
            services.trocarSenha(usuario, senhaAtual.getText().toString(), senhaNova.getText().toString());
        }catch(Exception e){
            senhaAtual.setError("Senha incorreta.");
            return;
        }
        Toast.makeText(this,"Senha alterado com sucesso!", Toast.LENGTH_SHORT).show();
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
