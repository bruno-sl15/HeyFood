package com.heyfood.heyfoodapp.pessoa.dominio;

import android.support.annotation.Nullable;

/**
 * Created by gabriel.caboclo on 14/05/2019.
 */

public enum TipoGenero {
    MASCULINO("Masculino"), FEMININO("Feminino"), OUTROS("Outros");
    private String TIPO_GENERO;

    TipoGenero(String tipoGenero) {
        this.TIPO_GENERO = tipoGenero;
    }

    public String getTipoGenero(){
        return TIPO_GENERO;
    }

    @Nullable
    public static final TipoGenero stringToEnum(String genero){
        if(genero.equals(TipoGenero.MASCULINO.toString())){
            return TipoGenero.MASCULINO;
        }else if(genero.equals(TipoGenero.FEMININO.toString())){
            return TipoGenero.FEMININO;
        }
        else if(genero.equals(TipoGenero.OUTROS.toString())){
            return TipoGenero.OUTROS;
        }
        return null;
    }

}
