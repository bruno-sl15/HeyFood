package com.heyfood.heyfoodapp.prato.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.prato.dominio.Prato;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.persistencia.RestauranteDAO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PratoDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PratoDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Prato getPrato(long id) {
        Prato result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO+ " WHERE " + DBHelper.CAMPO_ID_PRATO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createPrato(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public List<Prato> getPratoByRestaurante(long id){
        List<Prato> result = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PRATO + " WHERE " + DBHelper.CAMPO_FK_PRATO_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result.add(createPrato(cursor));
            while(cursor.moveToNext()){
                result.add(createPrato(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    //TODO: updatePrato(Prato prato) {}

    public long cadastrar(Prato prato) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME_PRATO, prato.getNome());
        values.put(DBHelper.CAMPO_DESCRICAO, prato.getDescricao());
        values.put(DBHelper.CAMPO_PRECO, prato.getPreco().doubleValue());
        values.put(DBHelper.CAMPO_FK_PRATO_RESTAURANTE, prato.getRestaurante().getId());
        long id = db.insert(DBHelper.TABELA_PRATO, null, values);
        super.close(db);
        return id;
    }

    private Prato createPrato(Cursor cursor){
        Prato result = new Prato();
        RestauranteDAO restauranteDAO = new RestauranteDAO((context));
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PRATO);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_PRATO);
        result.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DESCRICAO);
        result.setDescricao(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_PRECO);
        result.setPreco(BigDecimal.valueOf(cursor.getDouble(columnIndex)));
        //TODO: MÉTODO QUE CALCULE A NOTA MÉDIA PARA INCLUIR AQUI
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_PRATO_RESTAURANTE);
        result.setRestaurante(restauranteDAO.getRestaurante(cursor.getInt(columnIndex)));
        return result;
    }
}
