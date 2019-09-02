package com.heyfood.heyfoodapp.restaurante.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.InputMismatchException;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.contato.dominio.Contato;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.util.MaskEditUtil;

public class CadastrarRestauranteActivity extends AppCompatActivity {
    private EditText cnpj;
    private EditText cep;
    private EditText rua;
    private EditText numero;
    private EditText bairro;
    private EditText cidade;
    private EditText nome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_restaurante);

        cnpj = findViewById(R.id.campoCnpjId);
        nome = findViewById(R.id.campoNomeId);
        cep = findViewById(R.id.campoCepId);
        rua = findViewById(R.id.campoRuaId);
        numero = findViewById(R.id.campoNumeroId);
        bairro = findViewById(R.id.campoBairroId);
        cidade = findViewById(R.id.campoCidadeId);

        cnpj.addTextChangedListener(MaskEditUtil.mask(cnpj, MaskEditUtil.FORMAT_CNPJ));
        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));

        if (Sessao.instance.getRestaurante() != null){
            Restaurante restaura = Sessao.instance.getRestaurante();
            nome.setText(restaura.getNome());
            cnpj.setText(restaura.getCnpj());
            cep.setText(restaura.getEndereco().getCep());
            rua.setText(restaura.getEndereco().getRua());
            numero.setText(restaura.getEndereco().getNumero());
            bairro.setText(restaura.getEndereco().getBairro());
            cidade.setText(restaura.getEndereco().getCidade());
        }

    }

    @Override
    public void onBackPressed() {
        Sessao.instance.setRestaurante(createRestaurante());
        super.onBackPressed();
    }

    private boolean validarCnpj() {
        String CNPJ = this.cnpj.getText().toString().replace(".", "");
        CNPJ = CNPJ.replace("-","");
        CNPJ = CNPJ.replace("/","");
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))
            return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String imprimeCNPJ(String CNPJ) {
// máscara do CNPJ: 99.999.999.9999-99
        return(CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." +
                CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" +
                CNPJ.substring(12, 14));
    }

    private Endereco createEndereco(){
        Endereco endereco = new Endereco();
        endereco.setCep(cep.getText().toString());
        endereco.setRua(rua.getText().toString());
        endereco.setBairro(bairro.getText().toString());
        endereco.setCidade(cidade.getText().toString());
        endereco.setNumero(numero.getText().toString());

        return endereco;
    }

    private Restaurante createRestaurante(){
        Restaurante restaurante = new Restaurante();
        restaurante.setEndereco(createEndereco());
        restaurante.setNome(nome.getText().toString());
        restaurante.setCnpj(cnpj.getText().toString());

        return restaurante;
    }
    private boolean validarCampos() {
        return
                nome.getText().toString().length() != 0 &&
                        cep.getText().toString().length() != 0 &&
                        rua.getText().toString().length() != 0 &&
                        numero.getText().toString().length() != 0 &&
                        bairro.getText().toString().length() != 0 &&
                        cidade.getText().toString().length() != 0;
    }
    public void proximaTela1(View view) {
        if (!validarCampos()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (!validarCnpj()) {
            Toast.makeText(this, "CNPJ inválido.", Toast.LENGTH_LONG).show();
            return;
        }

        Sessao.instance.setRestaurante(createRestaurante());

        Intent novaTela = new Intent(this, ContatoRestauranteActivity.class);
        startActivity(novaTela);


    }


}