package com.heyfood.heyfoodapp.cliente.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.contato.ui.AtualizarContatoActivity;
import com.heyfood.heyfoodapp.endereco.ui.AtualizarEnderecoActivity;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.usuario.ui.TrocarSenhaActivity;

public class PerfilClienteActivity extends AppCompatActivity {

    private TextView nome;
    private TextView email;
    private TextView genero;
    private TextView cpf;
    private TextView nascimento;
    private TextView telefone;
    private TextView cep;
    private TextView rua;
    private TextView numero;
    private TextView bairro;
    private TextView cidade;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cliente);

        cliente = Sessao.instance.getCliente();

        nome = findViewById(R.id.nomeClienteId);
        email = findViewById(R.id.emailClienteId);
        genero = findViewById(R.id.generoClienteId);
        cpf = findViewById(R.id.cpfClienteId);
        nascimento = findViewById(R.id.nascimentoClienteId);
        telefone = findViewById(R.id.telefoneClienteId);
        cep = findViewById(R.id.cepClienteId);
        rua = findViewById(R.id.ruaClienteId);
        numero = findViewById(R.id.numeroClienteId);
        bairro = findViewById(R.id.bairroClienteId);
        cidade = findViewById(R.id.cidadeClienteId);

        nome.setText(String.format("Nome: %s",cliente.getUsuario().getPessoa().getNome()));
        email.setText(String.format("Email: %s",cliente.getUsuario().getLogin()));
        cpf.setText(String.format("CPF: %s",cliente.getUsuario().getPessoa().getCpf()));
        genero.setText(String.format("Gênero: %s",cliente.getUsuario().getPessoa().getGenero().toString()));
        nascimento.setText(String.format("Dada de nascimento: %s",cliente.getUsuario().getPessoa().getDataNascimento()));
        telefone.setText(String.format("Telefone: %s",cliente.getUsuario().getPessoa().getContato().getTelefone()));
        cep.setText(String.format("CEP: %s",cliente.getUsuario().getPessoa().getEndereco().getCep()));
        rua.setText(String.format("Rua: %s",cliente.getUsuario().getPessoa().getEndereco().getRua()));
        numero.setText(String.format("Numero: %s",cliente.getUsuario().getPessoa().getEndereco().getNumero()));
        bairro.setText(String.format("Bairro: %s",cliente.getUsuario().getPessoa().getEndereco().getBairro()));
        cidade.setText(String.format("Cidade: %s",cliente.getUsuario().getPessoa().getEndereco().getCidade()));
    }

    public void voltarHome(View view){
        Intent novaTela = new Intent(this, HomeClienteActivity.class);
        startActivity(novaTela);
    }

    public void trocarSenha(View view){
        Intent novaTela = new Intent(this, TrocarSenhaActivity.class);
        startActivity(novaTela);
    }

    public void atualizarEndereco(View view){
        Intent novaTela = new Intent(this, AtualizarEnderecoActivity.class);
        startActivity(novaTela);
    }

    public void atualizarContato(View view){
        Intent novaTela = new Intent(this, AtualizarContatoActivity.class);
        startActivity(novaTela);
    }

}
