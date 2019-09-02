package com.heyfood.heyfoodapp.proprietario.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.pessoa.dominio.TipoGenero;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.proprietario.negocio.ProprietarioServices;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;
import com.heyfood.heyfoodapp.pessoa.dominio.Pessoa;
import com.heyfood.heyfoodapp.usuario.dominio.Usuario;
import com.heyfood.heyfoodapp.usuario.ui.LoginActivity;
import com.heyfood.heyfoodapp.util.MaskEditUtil;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.infra.negocio.Valida;


public class CadastrarProprietarioActivity extends AppCompatActivity {
    private EditText nome;
    private EditText login;
    private EditText senha;
    private RadioGroup genero;
    private EditText dataNascimento;
    private EditText cpf;
    private EditText telefone;
    private EditText cep;
    private EditText rua;
    private EditText numero;
    private EditText bairro;
    private EditText cidade;
    private TipoGenero tipoGenero = TipoGenero.FEMININO;

    private final ProprietarioServices services = new ProprietarioServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_proprietario);

        //Inicializando objetos dos componentes de Layout
        nome = findViewById(R.id.campoNomeId);
        login = findViewById(R.id.campoLoginId);
        senha = findViewById(R.id.campoSenhaId);
        genero = findViewById(R.id.groupGeneroId);
        dataNascimento = findViewById(R.id.campoDataId);
        cpf = findViewById(R.id.campoCpfId);
        telefone = findViewById(R.id.campoTelefoneId);
        cep = findViewById(R.id.campoCepId);
        rua = findViewById(R.id.campoRuaId);
        numero = findViewById(R.id.campoNumeroId);
        bairro = findViewById(R.id.campoBairroId);
        cidade = findViewById(R.id.campoCidadeId);
        genero.check(R.id.radioFemininoId);
        genero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.radioMasculinoId){
                    tipoGenero = TipoGenero.MASCULINO;
                }
                else if(i == R.id.radioFemininoId){
                    tipoGenero = TipoGenero.FEMININO;
                }
                else{
                    tipoGenero = TipoGenero.OUTROS;
                }
            }
        });
        //Configurando mascara dos campos de Data e CPF
        dataNascimento.addTextChangedListener(MaskEditUtil.mask(dataNascimento, MaskEditUtil.FORMAT_DATE));
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));

        if (Sessao.instance.getProprietario() != null){
            Proprietario proprietarioRestaura = Sessao.instance.getProprietario();
            nome.setText(proprietarioRestaura.getUsuario().getPessoa().getNome());
            dataNascimento.setText(proprietarioRestaura.getUsuario().getPessoa().getDataNascimento());
            cpf.setText(proprietarioRestaura.getUsuario().getPessoa().getCpf());
            login.setText(proprietarioRestaura.getUsuario().getLogin());
            senha.setText(proprietarioRestaura.getUsuario().getSenha());
            telefone.setText(proprietarioRestaura.getUsuario().getPessoa().getContato().getTelefone());
            cep.setText(proprietarioRestaura.getUsuario().getPessoa().getEndereco().getCep());
            rua.setText(proprietarioRestaura.getUsuario().getPessoa().getEndereco().getRua());
            numero.setText(proprietarioRestaura.getUsuario().getPessoa().getEndereco().getNumero());
            bairro.setText(proprietarioRestaura.getUsuario().getPessoa().getEndereco().getBairro());
            cidade.setText(proprietarioRestaura.getUsuario().getPessoa().getEndereco().getCidade());
        }
    }

    @Override
    public void onBackPressed() {
        Sessao.instance.setProprietario(createProprietario());
        super.onBackPressed();
    }

    private Proprietario createProprietario(){
        Proprietario proprietario = new Proprietario();
        proprietario.setUsuario(createUsuario());
        return proprietario;
    }

    private Pessoa createPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setContato(createContato());
        pessoa.setEndereco(createEndereco());
        pessoa.setNome(nome.getText().toString());
        pessoa.setDataNAscimento(dataNascimento.getText().toString());
        pessoa.setCpf(cpf.getText().toString());
        pessoa.setGenero(tipoGenero);
        return pessoa;
    }

    private Usuario createUsuario(){
        Usuario usuario = new Usuario();
        usuario.setPessoa(createPessoa());
        usuario.setLogin(login.getText().toString());
        usuario.setSenha(senha.getText().toString());
        return usuario;
    }

    private Contato createContato(){
        Contato contato = new Contato();
        contato.setTelefone(telefone.getText().toString());
        contato.setEmail(login.getText().toString());
        contato.setSite("");
        return contato;
    }

    private Endereco createEndereco(){
        Endereco endereco = new Endereco();
        endereco.setCep(cep.getText().toString());
        endereco.setRua(rua.getText().toString());
        endereco.setNumero(numero.getText().toString());
        endereco.setBairro(bairro.getText().toString());
        endereco.setCidade(cidade.getText().toString());
        return endereco;
    }

    private boolean validarCampos(){
        return
            nome.getText().toString().length() != 0 &&
            login.getText().toString().length() != 0 &&
            senha.getText().toString().length() != 0 &&
            dataNascimento.getText().toString().length() != 0 &&
            cpf.getText().toString().length() !=0 &&
            telefone.getText().toString().length() != 0 &&
            cep.getText().toString().length() != 0 &&
            rua.getText().toString().length() != 0 &&
            numero.getText().toString().length() != 0 &&
            bairro.getText().toString().length() != 0 &&
            cidade.getText().toString().length() != 0;

    }

    public void cadastrar(View view) {
        if (!validarCampos()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Valida.validarEmail(login.getText().toString())) {
            Toast.makeText(this, "Email inválido.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Valida.validarData(dataNascimento.getText().toString())) {
            Toast.makeText(this, "Data inválida.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!Valida.validarCpf(cpf.getText().toString())) {
            Toast.makeText(this, "CPF inválido.", Toast.LENGTH_LONG).show();
            return;
        }
        Proprietario proprietario = createProprietario();

        try{
            services.cadastrar(proprietario);
            Intent novaTela = new Intent(this, LoginActivity.class);
            startActivity(novaTela);
            Toast.makeText(this, "Cadastro realizado", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(this, "Este login já existe", Toast.LENGTH_LONG).show();
        }

    }
}