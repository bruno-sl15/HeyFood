package com.heyfood.heyfoodapp.categoria.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;

public class EspecialidadeDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public EspecialidadeDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Categoria getEspecialidade(long id){
        Categoria result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_ESPECIALIDADES+ " WHERE " + DBHelper.CAMPO_ID_ESPECIALIDADES + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createPreferencia(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Categoria categoria){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_ACAI_ESP, booleanToInt(categoria.getAcai()));
        values.put(DBHelper.CAMPO_CHINESA_ESP, booleanToInt(categoria.getChinesa()));
        values.put(DBHelper.CAMPO_BRASILEIRA_ESP, booleanToInt(categoria.getBrasileira()));
        values.put(DBHelper.CAMPO_CARNES_ESP, booleanToInt(categoria.getCarnes()));
        values.put(DBHelper.CAMPO_CONTEMPORANEA_ESP, booleanToInt(categoria.getContemporanea()));
        values.put(DBHelper.CAMPO_ITALIANA_ESP, booleanToInt(categoria.getItaliana()));
        values.put(DBHelper.CAMPO_JAPONESA_ESP, booleanToInt(categoria.getJaponesa()));
        values.put(DBHelper.CAMPO_LANCHES_ESP, booleanToInt(categoria.getLanches()));
        values.put(DBHelper.CAMPO_MARMITA_ESP, booleanToInt(categoria.getMarmita()));
        values.put(DBHelper.CAMPO_PIZZA_ESP, booleanToInt(categoria.getPizza()));
        values.put(DBHelper.CAMPO_SAUDAVEL_ESP, booleanToInt(categoria.getSaudavel()));
        values.put(DBHelper.CAMPO_ALACARTE_ESP, booleanToInt(categoria.getAlacarte()));
        values.put(DBHelper.CAMPO_RODIZIO_ESP, booleanToInt(categoria.getRodizio()));
        values.put(DBHelper.CAMPO_DELIVERY_ESP, booleanToInt(categoria.getDelivery()));
        values.put(DBHelper.CAMPO_SELFSERVICE_ESP, booleanToInt(categoria.getSelfservice()));
        long id = db.insert(DBHelper.TABELA_ESPECIALIDADES, null, values);
        super.close(db);
        return id;
    }

    private int booleanToInt(boolean valor){
        if (valor){
            return 1;
        }else{
            return 0;
        }
    }

    private Boolean intToBoolean(int valor){
        if (valor == 1){
            return true;
        }else{
            return false;
        }
    }

    private Categoria createPreferencia(Cursor cursor){
        Categoria result = new Categoria();
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_ESPECIALIDADES);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ACAI_ESP);
        result.setAcai(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CHINESA_ESP);
        result.setChinesa(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_BRASILEIRA_ESP);
        result.setBrasileira(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CARNES_ESP);
        result.setCarnes(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CONTEMPORANEA_ESP);
        result.setContemporanea(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ITALIANA_ESP);
        result.setItaliana(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_JAPONESA_ESP);
        result.setJaponesa(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_LANCHES_ESP);
        result.setLanches(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_MARMITA_ESP);
        result.setMarmita(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_PIZZA_ESP);
        result.setPizza(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_SAUDAVEL_ESP);
        result.setSaudavel(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ALACARTE_ESP);
        result.setAlacarte(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_RODIZIO_ESP);
        result.setRodizio(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DELIVERY_ESP);
        result.setDelivery(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_SELFSERVICE_ESP);
        result.setSelfservice(intToBoolean(cursor.getInt(columnIndex)));
        return result;
    }
}
