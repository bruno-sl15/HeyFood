package com.heyfood.heyfoodapp.proprietario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.heyfood.heyfoodapp.usuario.persistencia.UsuarioDAO;
import com.heyfood.heyfoodapp.proprietario.dominio.Proprietario;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;

public class ProprietarioDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public ProprietarioDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Proprietario getProprietario(long fk_usuario) {
        Proprietario result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PROPRIETARIO + " WHERE " + DBHelper.CAMPO_FK_USUARIO_PROPRIETARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(fk_usuario)});
        if (cursor.moveToFirst()) {
            result = createProprietario(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Proprietario proprietario) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_USUARIO_PROPRIETARIO, proprietario.getUsuario().getId());
        long id = db.insert(DBHelper.TABELA_PROPRIETARIO, null, values);
        super.close(db);
        return id;
    }

    private Proprietario createProprietario(Cursor cursor){
        Proprietario result = new Proprietario();
        UsuarioDAO usuarioDAO = new UsuarioDAO((context));
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PROPRIETARIO);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_USUARIO_PROPRIETARIO);
        result.setUsuario(usuarioDAO.getUsuarioById(cursor.getInt(columnIndex)));
        return result;
    }
}
