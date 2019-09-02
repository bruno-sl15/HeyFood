package com.heyfood.heyfoodapp.restaurante.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.categoria.persistencia.EspecialidadeDAO;
import com.heyfood.heyfoodapp.contato.persistencia.ContatoDAO;
import com.heyfood.heyfoodapp.endereco.persistencia.EnderecoDAO;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestauranteDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public RestauranteDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Restaurante getRestaurante(long id) {
        Restaurante result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_RESTAURANTE + " WHERE " + DBHelper.CAMPO_ID_RESTAURANTE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createRestaurante(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public List<Restaurante> getListaRestaurantes() {
        List<Restaurante> result = new ArrayList<Restaurante>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_RESTAURANTE;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if (cursor.moveToFirst()) {
            result.add(createRestaurante(cursor));
            while(cursor.moveToNext()){
                result.add(createRestaurante(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    public List<Restaurante> getListaMeusRestaurantes(long id) {
        List<Restaurante> result = new ArrayList<Restaurante>();
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_RESTAURANTE + " WHERE " + DBHelper.CAMPO_FK_PROPRIETARIO + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result.add(createRestaurante(cursor));
            while(cursor.moveToNext()){
                result.add(createRestaurante(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Restaurante restaurante) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME_RESTAURANTE, restaurante.getNome());
        values.put(DBHelper.CAMPO_CNPJ, restaurante.getCnpj());
        //values.put(DBHelper.CAMPO_NOTA_MEDIA, restaurante.getNotaMedia());
        values.put(DBHelper.CAMPO_FK_ENDERECO_RESTAURANTE, restaurante.getEndereco().getId());
        values.put(DBHelper.CAMPO_FK_CONTATO_RESTAURANTE, restaurante.getContato().getId());
        values.put(DBHelper.CAMPO_FK_ESPECIALIDADES, restaurante.getEspecialidades().getId());
        values.put(DBHelper.CAMPO_FK_PROPRIETARIO, restaurante.getProprietario().getId());
        long id = db.insert(DBHelper.TABELA_RESTAURANTE, null, values);
        super.close(db);
        return id;
    }
    //http://www.sqlitetutorial.net/sqlite-inner-join/

    public List<Restaurante> getRestaurantesByCidade(String cidade) {
        List<Restaurante> result = new ArrayList<Restaurante>();
        db = helper.getReadableDatabase();
        //String sql = "SELECT * FROM " + DBHelper.TABELA_RESTAURANTE + " WHERE " + DBHelper.CAMPO_FK_PROPRIETARIO + " LIKE ?;";
        //Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        String sql = "SELECT * FROM " + DBHelper.TABELA_RESTAURANTE + " INNER JOIN "
                + DBHelper.TABELA_ENDERECO
                + " ON " + DBHelper.CAMPO_FK_ENDERECO_RESTAURANTE + " = "
                +  DBHelper.TABELA_ENDERECO + "." +DBHelper.CAMPO_ID_ENDERECO
                + " WHERE " + DBHelper.CAMPO_CIDADE + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{cidade});
        if (cursor.moveToFirst()) {
            result.add(createRestaurante(cursor));
            while(cursor.moveToNext()){
                result.add(createRestaurante(cursor));
            }
        }
        super.close(cursor, db);
        return result;
    }

    private Restaurante createRestaurante(Cursor cursor){
        Restaurante result = new Restaurante();
        EnderecoDAO enderecoDAO = new EnderecoDAO(context);
        ContatoDAO contatoDAO = new ContatoDAO(context);
        EspecialidadeDAO especialidadesDAO = new EspecialidadeDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_RESTAURANTE);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME_RESTAURANTE);
        result.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CNPJ);
        result.setCnpj(cursor.getString(columnIndex));
        //columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOTA_MEDIA);
        //result.setNotaMedia(cursor.getFloat(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ENDERECO_RESTAURANTE);
        result.setEndereco(enderecoDAO.getEndereco(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CONTATO_RESTAURANTE);
        result.setContato(contatoDAO.getContato(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ESPECIALIDADES);
        result.setEspecialidades(especialidadesDAO.getEspecialidade(cursor.getInt(columnIndex)));
        return result;
    }

}
