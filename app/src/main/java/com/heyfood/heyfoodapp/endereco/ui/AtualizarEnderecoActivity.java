package com.heyfood.heyfoodapp.endereco.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.ui.PerfilClienteActivity;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;
import com.heyfood.heyfoodapp.endereco.persistencia.EnderecoDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.proprietario.ui.PerfilProprietarioActivity;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

public class AtualizarEnderecoActivity extends AppCompatActivity {
    private Cliente cliente;
    private Proprietario proprietario;
    private Endereco endereco;
    private EditText cep;
    private EditText rua;
    private EditText numero;
    private EditText bairro;
    private EditText cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_endereco);
        cliente = Sessao.instance.getCliente();
        proprietario = Sessao.instance.getProprietario();
        cep = findViewById(R.id.campoCepId);
        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));
        rua = findViewById(R.id.campoRuaId);
        numero = findViewById(R.id.campoNumeroId);
        bairro = findViewById(R.id.campoBairroId);
        cidade = findViewById(R.id.campoCidadeId);
        if(cliente != null){
            endereco = cliente.getUsuario().getPessoa().getEndereco();
        }
        else{
            endereco = proprietario.getUsuario().getPessoa().getEndereco();
        }
        cep.setText(endereco.getCep());
        rua.setText(endereco.getRua());
        numero.setText(endereco.getNumero());
        bairro.setText(endereco.getBairro());
        cidade.setText(endereco.getCidade());
    }

    private void updateEndereco(){
        endereco.setCep(cep.getText().toString());
        endereco.setRua(rua.getText().toString());
        endereco.setNumero(numero.getText().toString());
        endereco.setBairro(bairro.getText().toString());
        endereco.setCidade(cidade.getText().toString());
    }

    private boolean validarCampos(){
        return cep.length()!=0 &&
                rua.length()!=0 &&
                numero.length()!=0 &&
                bairro.length()!=0 &&
                cidade.length()!=0;
    }

    public void atualizar(View view){
        if(!validarCampos()){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        EnderecoDAO enderecoDAO = new EnderecoDAO(this);
        updateEndereco();
        enderecoDAO.updateEndereco(endereco);
        Toast.makeText(this, "Endereço alterado com sucesso!", Toast.LENGTH_SHORT).show();
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
