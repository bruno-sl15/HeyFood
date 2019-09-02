package com.heyfood.heyfoodapp.avaliacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoRestaurante;
import com.heyfood.heyfoodapp.cliente.persistencia.ClienteDAO;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.restaurante.persistencia.RestauranteDAO;

import java.util.ArrayList;

public class AvaliacaoRestauranteDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public AvaliacaoRestauranteDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public AvaliacaoRestaurante getAvaliacao(long idCliente, long idRestaurante) {
        AvaliacaoRestaurante result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACAO_RESTAURANTE + " WHERE " + DBHelper.CAMPO_FK_RESTAURANTE + " LIKE ? AND "
                + DBHelper.CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(idRestaurante), Long.toString(idCliente)});
        if (cursor.moveToFirst()) {
            result = createAvaliacao(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(AvaliacaoRestaurante avaliacao) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_RESTAURANTE, avaliacao.getRestaurante().getId());
        values.put(DBHelper.CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE, avaliacao.getCliente().getId());
        values.put(DBHelper.CAMPO_NOTA_RESTAURANTE, avaliacao.getNota());
        long id = db.insert(DBHelper.TABELA_AVALIACAO_RESTAURANTE, null, values);
        super.close(db);
        return id;
    }

    public float getValorAvaliacao(long idCliente, long idRestaurante){
        Float nota;
        AvaliacaoRestaurante result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACAO_RESTAURANTE + " WHERE " + DBHelper.CAMPO_FK_RESTAURANTE + " LIKE ? AND "
                + DBHelper.CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(idRestaurante), Long.toString(idCliente)});
        if (cursor.moveToFirst()) {
            result = createAvaliacao(cursor);
        }
        if(result != null){
            nota = result.getNota();
        }
        else{
            nota = -1.0f;
        }
        super.close(cursor, db);
        return nota;
    }

    public ArrayList<AvaliacaoRestaurante> getAvaliacoes(long id) {
        ArrayList<AvaliacaoRestaurante> result = new ArrayList<>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACAO_RESTAURANTE + " WHERE " + DBHelper.CAMPO_FK_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result.add(createAvaliacao(cursor));
            while(cursor.moveToNext()){
                result.add(createAvaliacao(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    public void updateNota(AvaliacaoRestaurante avaliacao) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOTA_RESTAURANTE, avaliacao.getNota());
        String[] id = new String[]{Long.toString(avaliacao.getId())};
        db.update(DBHelper.TABELA_AVALIACAO_RESTAURANTE, values, DBHelper.CAMPO_ID_AVALIACAO_RESTAURANTE+" = ?;", id);
        super.close();
    }

    private AvaliacaoRestaurante createAvaliacao(Cursor cursor){
        AvaliacaoRestaurante result = new AvaliacaoRestaurante();
        RestauranteDAO restauranteDAO = new RestauranteDAO(context);
        ClienteDAO clienteDAO = new ClienteDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_AVALIACAO_RESTAURANTE);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_RESTAURANTE);
        result.setRestaurante(restauranteDAO.getRestaurante(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE);
        result.setCliente(clienteDAO.getClienteById(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOTA_RESTAURANTE);
        result.setNota(Float.parseFloat(cursor.getString(columnIndex)));
        return result;
    }

}
