package com.heyfood.heyfoodapp.restaurante.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.contato.persistencia.ContatoDAO;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;
import com.heyfood.heyfoodapp.endereco.persistencia.EnderecoDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.proprietario.ui.HomeProprietarioActivity;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

public class AtualizarRestauranteActivity extends AppCompatActivity {

    EditText nome, telefone, email, cep, rua, numero, bairro, cidade;
    Restaurante restaurante = Sessao.instance.getRestaurante();
    private Contato contato;
    private ContatoDAO contatoDAO;
    private EnderecoDAO enderecoDAO;
    private Endereco endereco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_restaurante);
        contato = Sessao.instance.getRestaurante().getContato();
        endereco = Sessao.instance.getRestaurante().getEndereco();

        nome = findViewById(R.id.campoNomeId);
        telefone = findViewById(R.id.campoTelefoneId);
        email = findViewById(R.id.campoEmailId);
        cep = findViewById(R.id.campoCepId);
        rua = findViewById(R.id.campoRuaId);
        numero = findViewById(R.id.campoNumeroId);
        bairro = findViewById(R.id.campoBairroId);
        cidade = findViewById(R.id.campoCidadeId);

        nome.setText(restaurante.getNome());
        telefone.setText(restaurante.getContato().getTelefone());
        email.setText(restaurante.getContato().getEmail());
        cep.setText(restaurante.getEndereco().getCep());
        rua.setText(restaurante.getEndereco().getRua());
        numero.setText(restaurante.getEndereco().getNumero());
        bairro.setText(restaurante.getEndereco().getBairro());
        cidade.setText(restaurante.getEndereco().getCidade());

        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));


    }

    private void updateEndereco(){
        endereco.setCep(cep.getText().toString());
        endereco.setRua(rua.getText().toString());
        endereco.setNumero(numero.getText().toString());
        endereco.setBairro(bairro.getText().toString());
        endereco.setCidade(cidade.getText().toString());
    }

    public boolean validarCampos(){
        return nome.length() != 0 && telefone.length()!= 0
                && email.length() != 0 && cep.length()!= 0
                && rua.length()!=0 && numero.length()!=0
                && bairro.length()!=0 && cidade.length()!=0;
    }

    public void updateContato(){
        contato.setEmail(email.getText().toString());
        contato.setTelefone(telefone.getText().toString());
    }

    public void atualizarRestaurante(View view){
        if (validarCampos()!= true){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
        ContatoDAO contatoDAO = new ContatoDAO(this);
        updateContato();
        contatoDAO.updateContato(contato);

        EnderecoDAO enderecoDAO = new EnderecoDAO(this);
        updateEndereco();
        enderecoDAO.updateEndereco(endereco);

        Intent novaTela = new Intent(this, ListarRestaurantes.class);
        startActivity(novaTela);
        Toast.makeText(this, "Restaurante atualizado com sucesso!", Toast.LENGTH_LONG).show();


    }
}
