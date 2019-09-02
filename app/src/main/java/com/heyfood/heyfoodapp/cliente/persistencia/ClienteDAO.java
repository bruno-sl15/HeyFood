package com.heyfood.heyfoodapp.cliente.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.categoria.persistencia.PreferenciaDAO;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.usuario.persistencia.UsuarioDAO;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GABRIEL.CABOCLO on 29/05/2019.
 */

public class ClienteDAO extends AbstractDAO {
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public ClienteDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Cliente getCliente(long fk_usuario) {
        Cliente result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CLIENTE+ " WHERE " + DBHelper.CAMPO_FK_USUARIO_CLIENTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(fk_usuario)});
        if (cursor.moveToFirst()) {
            result = createCliente(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public Cliente getClienteById(long id) {
        Cliente result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CLIENTE+ " WHERE " + DBHelper.CAMPO_ID_CLIENTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createCliente(cursor);
        }
        super.close(cursor, db);
        return result;
    }


    public List<Cliente> getListaClientes() {
        List<Cliente> result = new ArrayList<Cliente>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_CLIENTE;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            result.add(createCliente(cursor));
            while(cursor.moveToNext()){
                result.add(createCliente(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Cliente cliente) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_USUARIO_CLIENTE, cliente.getUsuario().getId());
        long id = db.insert(DBHelper.TABELA_CLIENTE, null, values);
        super.close(db);
        return id;
    }

    public void setPreferencias(long idPreferencias){
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_FK_PREFERENCIAS, idPreferencias);
        String[] idCliente = new String[]{Long.toString(Sessao.instance.getCliente().getId())};
        db.update(DBHelper.TABELA_CLIENTE, values, DBHelper.CAMPO_ID_CLIENTE+"=?", idCliente);
        super.close();
    }

    private Cliente createCliente(Cursor cursor){
        Cliente result = new Cliente();
        UsuarioDAO usuarioDAO = new UsuarioDAO(context);
        PreferenciaDAO preferenciasDAO = new PreferenciaDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_CLIENTE);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_USUARIO_CLIENTE);
        result.setUsuario(usuarioDAO.getUsuarioById(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_PREFERENCIAS);
        result.setPreferencias(preferenciasDAO.getPreferencia(cursor.getInt(columnIndex)));
        return result;
    }

}
