package com.heyfood.heyfoodapp.endereco.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.endereco.dominio.Endereco;

public class EnderecoDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public EnderecoDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Endereco getEndereco(long id) {
        Endereco result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_ENDERECO+ " WHERE " + DBHelper.CAMPO_ID_ENDERECO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createEndereco(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public void updateEndereco(Endereco endereco){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_CEP, endereco.getCep());
        values.put(DBHelper.CAMPO_RUA, endereco.getRua());
        values.put(DBHelper.CAMPO_NUMERO, endereco.getNumero());
        values.put(DBHelper.CAMPO_BAIRRO, endereco.getBairro());
        values.put(DBHelper.CAMPO_CIDADE, endereco.getCidade());
        String[] id = new String[]{Long.toString(endereco.getId())};
        db.update(DBHelper.TABELA_ENDERECO, values, DBHelper.CAMPO_ID_ENDERECO+"=?", id);
        super.close();
    }

    public long cadastrar(Endereco endereco) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_CEP, endereco.getCep());
        values.put(DBHelper.CAMPO_RUA, endereco.getRua());
        values.put(DBHelper.CAMPO_BAIRRO, endereco.getBairro());
        values.put(DBHelper.CAMPO_CIDADE, endereco.getCidade());
        values.put(DBHelper.CAMPO_NUMERO, endereco.getNumero());
        long id = db.insert(DBHelper.TABELA_ENDERECO, null, values);
        super.close(db);
        return id;
    }

    private Endereco createEndereco(Cursor cursor){
        Endereco result = new Endereco();
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_ENDERECO);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CEP);
        result.setCep(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_RUA);
        result.setRua(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_BAIRRO);
        result.setBairro(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CIDADE);
        result.setCidade(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NUMERO);
        result.setNumero(cursor.getString(columnIndex));
        return result;
    }
}
