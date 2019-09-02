package com.heyfood.heyfoodapp;

import org.junit.Test;

import com.google.common.truth.Truth;
import com.heyfood.heyfoodapp.infra.negocio.Valida;

public class DateValidatorTest {
    @Test
    public void dateValidator_CorrectDateSimple_ReturnTrue(){
        Truth.assertThat(Valida.validarData("01/02/2001")).isTrue();
    }
}
