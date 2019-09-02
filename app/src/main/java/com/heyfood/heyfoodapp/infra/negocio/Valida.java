package com.heyfood.heyfoodapp.infra.negocio;

import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Valida {
    public static boolean validarCpf(String cpf){
        // Verifica se o campo está preenchido com 14 digitos
        //incluindo pontos e o ífem
        if (cpf.length()<14){
            return false;
        }
        // Remove os pontos
        String campoCpf = cpf.replace(".", "");
        // Remove o ífem
        campoCpf = campoCpf.replace("-","");
        //int cpf = Integer.parseInt(campoCpf);
        int soma = 0;
        // VERIFICAÇÃO DO PENÚLTIMO DÍGITO
        // Multiplica os digitos por 10, 9, 8,...,2
        // Cada digito é multiplicado por um valor e cada resultudO é somado
        for (int i=10, j=0 ; i>1 ; i--, j++){
            soma += Integer.parseInt(campoCpf.substring(j, j+1)) * i;
        }
        // Calcular o resto da divisão
        // Se for igual a 10, o resto será 0
        if((soma*10)%11 == 10){
            soma = 0;
        }
        // Verificar se o resto da divisão é igual ao penúltimo dígito
        if (!((soma*10)%11 == Integer.parseInt(campoCpf.substring(9,10)))){
            return false;
        }
        soma = 0;
        // VERIFICAÇÃO DO ÚLTIMO DÍGITO
        // Multiplica os digitos por 11, 10, 9,...,2
        // Cada digito é multiplicado por um valor e cada resultudO é somado
        for (int i=11, j=0 ; i>1 ; i--, j++) {
            soma += Integer.parseInt(campoCpf.substring(j, j + 1)) * i;
        }
        // Calcular o resto da divisão
        // Se for igual a 10, o resto será 0
        if((soma*10)%11 == 10) {
            soma = 0;
        }
        // Verificar se o resto da divisão é igual ao último dígito
        if (!((soma*10)%11 == Integer.parseInt(campoCpf.substring(10)))){
            return false;
        }
        if (campoCpf.equals("00000000000") || campoCpf.equals("11111111111") ||
                campoCpf.equals("22222222222") || campoCpf.equals("33333333333") ||
                campoCpf.equals("44444444444") || campoCpf.equals("55555555555") ||
                campoCpf.equals("66666666666") || campoCpf.equals("77777777777") ||
                campoCpf.equals("88888888888") || campoCpf.equals("99999999999")){
            return false;}
        return true;

    }

    public static boolean validarEmail(String email){
        // Verifica se tem '@' e '.com'
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        return false;
    }

    public static boolean validarData(String data) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); // Formatar datas
        format.setLenient(false); // Quando seta para "falso", datas incorretas não são aceitas

        try {
            Date date = format.parse(data);
            // Se converter o objeto para o tipo Date
            // É porque a data está no formato correto
            return true;
        } catch (ParseException e) {
            return false;

        }
    }
}