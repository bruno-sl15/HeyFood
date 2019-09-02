package com.heyfood.heyfoodapp.restaurante.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.heyfood.heyfoodapp.util.MaskEditUtil;
import com.heyfood.heyfoodapp.R;

public class InformacoesRestauranteActivity extends AppCompatActivity {
    private EditText abre;
    private EditText fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_restaurante);
        abre = findViewById(R.id.horaAbreId);
        fecha = findViewById(R.id.horaFechaId);

        abre.addTextChangedListener(MaskEditUtil.mask(abre, MaskEditUtil.FORMAT_HOUR));
        fecha.addTextChangedListener(MaskEditUtil.mask(fecha, MaskEditUtil.FORMAT_HOUR));
    }

    private boolean validarHora() {
        String horaAbre1 = abre.getText().toString();
        horaAbre1 = horaAbre1.replace(":", "");
        String horaFecha1 = fecha.getText().toString();
        horaFecha1 = horaFecha1.replace(":", "");
        int valido = Integer.parseInt(horaAbre1);
        int valido2 = Integer.parseInt(horaFecha1);
        if (valido >= 0 && valido <= 2359 && valido2 >= 0 && valido2 <= 2359) {
            return true;
        }else {
            return false;
        }

    }





}
