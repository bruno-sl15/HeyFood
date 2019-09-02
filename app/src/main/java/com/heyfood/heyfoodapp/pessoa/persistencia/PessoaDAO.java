package com.heyfood.heyfoodapp.pessoa.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.heyfood.heyfoodapp.contato.persistencia.ContatoDAO;
import com.heyfood.heyfoodapp.endereco.persistencia.EnderecoDAO;
import com.heyfood.heyfoodapp.infra.persistencia.AbstractDAO;
import com.heyfood.heyfoodapp.infra.persistencia.DBHelper;
import com.heyfood.heyfoodapp.pessoa.dominio.Pessoa;
import com.heyfood.heyfoodapp.pessoa.dominio.TipoGenero;

public class PessoaDAO extends AbstractDAO{
    private SQLiteDatabase db;
    private DBHelper helper;
    private Context context;

    public PessoaDAO(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public Pessoa getPessoa(long id) {
        Pessoa result = null;
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_PESSOA+ " WHERE " + DBHelper.CAMPO_ID_PESSOA + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{Long.toString(id)});
        if (cursor.moveToFirst()) {
            result = createPessoa(cursor);
        }
        super.close(cursor, db);
        return result;
    }

    public long cadastrar(Pessoa pessoa) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.CAMPO_NOME, pessoa.getNome());
        values.put(DBHelper.CAMPO_CPF, pessoa.getCpf());
        values.put(DBHelper.CAMPO_DATA_NASCIMENTO, pessoa.getDataNascimento());
        values.put(DBHelper.CAMPO_GENERO, pessoa.getGenero().toString());
        values.put(DBHelper.CAMPO_FK_ENDERECO, pessoa.getEndereco().getId());
        values.put(DBHelper.CAMPO_FK_CONTATO, pessoa.getContato().getId());
        long id = db.insert(DBHelper.TABELA_PESSOA, null, values);
        super.close(db);
        return id;
    }

    private Pessoa createPessoa(Cursor cursor){
        Pessoa result = new Pessoa();
        EnderecoDAO enderecoDAO = new EnderecoDAO(context);
        ContatoDAO contatoDAO = new ContatoDAO(context);
        int columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_ID_PESSOA);
        result.setId(Integer.parseInt(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_NOME);
        result.setNome(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_CPF);
        result.setCpf(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_DATA_NASCIMENTO);
        result.setDataNAscimento(cursor.getString(columnIndex));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_GENERO);
        result.setGenero(TipoGenero.stringToEnum(cursor.getString(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_ENDERECO);
        result.setEndereco(enderecoDAO.getEndereco(cursor.getInt(columnIndex)));
        columnIndex = cursor.getColumnIndex(DBHelper.CAMPO_FK_CONTATO);
        result.setContato(contatoDAO.getContato(cursor.getInt(columnIndex)));
        return result;
    }
}
