package com.heyfood.heyfoodapp.avaliacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoPrato;
import com.heyfood.heyfoodapp.cliente.persistencia.ClienteDAO;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.prato.persistencia.PratoDAO;

public class AvaliacaoPratoDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public AvaliacaoPratoDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public AvaliacaoPrato getAvaliacao(long id) {
        AvaliacaoPrato result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACAO_RESTAURANTE + " WHERE " + DBHelper.CAMPO_ID_AVALIACAO_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createAvaliacao(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(AvaliacaoPrato avaliacao) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_PRATO, avaliacao.getPrato().getId());
        values.put(DBHelper.CAMPO_FK_CLIENTE_AVALIA_PRATO, avaliacao.getCliente().getId());
        values.put(DBHelper.CAMPO_NOTA_PRATO, avaliacao.getNota());
        long id = db.insert(DBHelper.TABELA_AVALIACAO_PRATO, null, values);
        super.close(db);
        return id;
    }

    private AvaliacaoPrato createAvaliacao(Cursor cursor){
        AvaliacaoPrato result = new AvaliacaoPrato();
        PratoDAO pratoDAO = new PratoDAO(context);
        ClienteDAO clienteDAO = new ClienteDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_AVALIACAO_PRATO);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_PRATO);
        result.setPrato(pratoDAO.getPrato(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CLIENTE_AVALIA_PRATO);
        result.setCliente(clienteDAO.getCliente(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOTA_PRATO);
        result.setNota(Integer.parseInt(cursor.getString(columnIndex)));
        return result;
    }

}
