package com.heyfood.heyfoodapp.categoria.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.categoria.dominio.Categoria;
import com.heyfood.heyfoodapp.cliente.persistencia.ClienteDAO;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;

public class PreferenciaDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PreferenciaDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Categoria getPreferencia(long id){
        Categoria result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PREFERENCIAS+ " WHERE " + DBHelper.CAMPO_ID_PREFERENCIAS + " LIKE ?;";
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

        values.put(DBHelper.CAMPO_ACAI_PREF, booleanToInt(categoria.getAcai()));
        values.put(DBHelper.CAMPO_CHINESA_PREF, booleanToInt(categoria.getChinesa()));
        values.put(DBHelper.CAMPO_BRASILEIRA_PREF, booleanToInt(categoria.getBrasileira()));
        values.put(DBHelper.CAMPO_CARNES_PREF, booleanToInt(categoria.getCarnes()));
        values.put(DBHelper.CAMPO_CONTEMPORANEA_PREF, booleanToInt(categoria.getContemporanea()));
        values.put(DBHelper.CAMPO_ITALIANA_PREF, booleanToInt(categoria.getItaliana()));
        values.put(DBHelper.CAMPO_JAPONESA_PREF, booleanToInt(categoria.getJaponesa()));
        values.put(DBHelper.CAMPO_LANCHES_PREF, booleanToInt(categoria.getLanches()));
        values.put(DBHelper.CAMPO_MARMITA_PREF, booleanToInt(categoria.getMarmita()));
        values.put(DBHelper.CAMPO_PIZZA_PREF, booleanToInt(categoria.getPizza()));
        values.put(DBHelper.CAMPO_SAUDAVEL_PREF, booleanToInt(categoria.getSaudavel()));
        values.put(DBHelper.CAMPO_ALACARTE_PREF, booleanToInt(categoria.getAlacarte()));
        values.put(DBHelper.CAMPO_RODIZIO_PREF, booleanToInt(categoria.getRodizio()));
        values.put(DBHelper.CAMPO_DELIVERY_PREF, booleanToInt(categoria.getDelivery()));
        values.put(DBHelper.CAMPO_SELFSERVICE_PREF, booleanToInt(categoria.getSelfservice()));
        long id = db.insert(DBHelper.TABELA_PREFERENCIAS, null, values);
        ClienteDAO clienteDAO = new ClienteDAO(context);
        clienteDAO.setPreferencias(id);
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
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PREFERENCIAS);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ACAI_PREF);
        result.setAcai(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CHINESA_PREF);
        result.setChinesa(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_BRASILEIRA_PREF);
        result.setBrasileira(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CARNES_PREF);
        result.setCarnes(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CONTEMPORANEA_PREF);
        result.setContemporanea(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ITALIANA_PREF);
        result.setItaliana(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_JAPONESA_PREF);
        result.setJaponesa(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_LANCHES_PREF);
        result.setLanches(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_MARMITA_PREF);
        result.setMarmita(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_PIZZA_PREF);
        result.setPizza(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_SAUDAVEL_PREF);
        result.setSaudavel(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ALACARTE_PREF);
        result.setAlacarte(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_RODIZIO_PREF);
        result.setRodizio(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DELIVERY_PREF);
        result.setDelivery(intToBoolean(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_SELFSERVICE_PREF);
        result.setSelfservice(intToBoolean(cursor.getInt(columnIndex)));
        return result;
    }
}
