package com.heyfood.heyfoodapp.infra.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String NOME_DB = "heyfood.db";
    public static final int VERSAO = 1;
    public static final String TAG = "SQLite";

    //Tabela Cliente
    public static final String TABELA_CLIENTE = "tb_cliente";
    public static final String CAMPO_ID_CLIENTE = "id";
    public static final String CAMPO_FK_USUARIO_CLIENTE = "fk_usuario";
    public static final String CAMPO_FK_PREFERENCIAS = "fk_preferencias";


    //Tabela Pessoa
    public static final String TABELA_PESSOA = "tb_pessoa";
    public static final String CAMPO_ID_PESSOA = "id";
    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_CPF = "cpf";
    public static final String CAMPO_DATA_NASCIMENTO = "data_nascimento";
    public static final String CAMPO_GENERO = "genero";
    public static final String CAMPO_FK_ENDERECO = "fk_endereco";
    public static final String CAMPO_FK_CONTATO = "fk_contato";

    //Tabela Usuario
    public static final String TABELA_USUARIO = "tb_usuario";
    public static final String CAMPO_ID_USUARIO = "id";
    public static final String CAMPO_FK_PESSOA = "fk_pessoa";
    public static final String CAMPO_LOGIN = "login";
    public static final String CAMPO_SENHA = "senha";

    //Tabela Endereço
    public static final String TABELA_ENDERECO = "tb_endereco";
    public static final String CAMPO_ID_ENDERECO = "id";
    public static final String CAMPO_CEP = "cep";
    public static final String CAMPO_RUA = "rua";
    public static final String CAMPO_BAIRRO = "bairro";
    public static final String CAMPO_CIDADE= "cidade";
    public static final String CAMPO_NUMERO = "numero";

    //Tabela Contato
    public static final String TABELA_CONTATO = "tb_contato";
    public static final String CAMPO_ID_CONTATO = "id";
    public static final String CAMPO_TELEFONE = "telefone";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_SITE = "site";

    //Tabela Restaurante
    public static final String TABELA_RESTAURANTE = "tb_restaurante";
    public static final String CAMPO_ID_RESTAURANTE = "id";
    public static final String CAMPO_NOME_RESTAURANTE = "nome";
    public static final String CAMPO_CNPJ = "cnpj";
    public static final String CAMPO_NOTA_MEDIA = "nota_media";
    public static final String CAMPO_FK_ENDERECO_RESTAURANTE = "fk_endereco";
    public static final String CAMPO_FK_CONTATO_RESTAURANTE = "fk_contato";
    public static final String CAMPO_FK_ESPECIALIDADES = "fk_especialidades";
    public static final String CAMPO_FK_PROPRIETARIO = "fk_proprietario";

    //Tabela Proprietario
    public static final String TABELA_PROPRIETARIO = "tb_proprietario";
    public static final String CAMPO_ID_PROPRIETARIO = "id";
    public static final String CAMPO_FK_USUARIO_PROPRIETARIO = "fk_usuario";

    //Tabela Especialidades
    public static final String TABELA_ESPECIALIDADES = "tb_especialidades";
    public static final String CAMPO_ID_ESPECIALIDADES = "id";
    public static final String CAMPO_ACAI_ESP = "acai";
    public static final String CAMPO_CHINESA_ESP = "chinesa";
    public static final String CAMPO_BRASILEIRA_ESP = "brasileira";
    public static final String CAMPO_CARNES_ESP = "carnes";
    public static final String CAMPO_CONTEMPORANEA_ESP = "contemporanea";
    public static final String CAMPO_ITALIANA_ESP = "italiana";
    public static final String CAMPO_JAPONESA_ESP = "japonesa";
    public static final String CAMPO_LANCHES_ESP = "lanches";
    public static final String CAMPO_MARMITA_ESP = "marmita";
    public static final String CAMPO_PIZZA_ESP = "pizza";
    public static final String CAMPO_SAUDAVEL_ESP = "saudavel";
    public static final String CAMPO_ALACARTE_ESP = "alacarte";
    public static final String CAMPO_RODIZIO_ESP = "rodizio";
    public static final String CAMPO_DELIVERY_ESP = "delivery";
    public static final String CAMPO_SELFSERVICE_ESP = "selfservice";


    //Tabela Preferências
    public static final String TABELA_PREFERENCIAS = "tb_preferencias";
    public static final String CAMPO_ID_PREFERENCIAS = "id";
    public static final String CAMPO_ACAI_PREF = "acai";
    public static final String CAMPO_CHINESA_PREF = "chinesa";
    public static final String CAMPO_BRASILEIRA_PREF = "brasileira";
    public static final String CAMPO_CARNES_PREF = "carnes";
    public static final String CAMPO_CONTEMPORANEA_PREF = "contemporanea";
    public static final String CAMPO_ITALIANA_PREF = "italiana";
    public static final String CAMPO_JAPONESA_PREF = "japonesa";
    public static final String CAMPO_LANCHES_PREF = "lanches";
    public static final String CAMPO_MARMITA_PREF = "marmita";
    public static final String CAMPO_PIZZA_PREF = "pizza";
    public static final String CAMPO_SAUDAVEL_PREF = "saudavel";
    public static final String CAMPO_ALACARTE_PREF = "alacarte";
    public static final String CAMPO_RODIZIO_PREF = "rodizio";
    public static final String CAMPO_DELIVERY_PREF = "delivery";
    public static final String CAMPO_SELFSERVICE_PREF = "selfservice";

    //Tabela Pratos
    public static final String TABELA_PRATO = "tb_prato";
    public static final String CAMPO_ID_PRATO = "id";
    public static final String CAMPO_NOME_PRATO = "nome";
    public static final String CAMPO_DESCRICAO = "descricao";
    public static final String CAMPO_PRECO = "preco";
    public static final String CAMPO_FK_PRATO_RESTAURANTE = "restaurante";
    public static final String CAMPO_PRATO_NOTA_MEDIA = "media";

    //Tabela Avaliacao Restaurante
    public static final String TABELA_AVALIACAO_RESTAURANTE = "tb_avaliacao_restaurante";
    public static final String CAMPO_ID_AVALIACAO_RESTAURANTE = "id";
    public static final String CAMPO_FK_RESTAURANTE = "fk_restaurante";
    public static final String CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE = "fk_cliente";
    public static final String CAMPO_NOTA_RESTAURANTE = "nota";

    //Tabela Avaliacao Prato
    public static final String TABELA_AVALIACAO_PRATO = "tb_avaliacao_prato";
    public static final String CAMPO_ID_AVALIACAO_PRATO = "id";
    public static final String CAMPO_FK_PRATO = "fk_prato";
    public static final String CAMPO_FK_CLIENTE_AVALIA_PRATO = "fk_cliente";
    public static final String CAMPO_NOTA_PRATO = "nota";

    private static final String[] TABELAS = {
            TABELA_CLIENTE, TABELA_PESSOA, TABELA_USUARIO, TABELA_ENDERECO, TABELA_CONTATO, TABELA_RESTAURANTE, TABELA_PROPRIETARIO, TABELA_ESPECIALIDADES, TABELA_PREFERENCIAS, TABELA_PRATO, TABELA_AVALIACAO_RESTAURANTE, TABELA_AVALIACAO_PRATO
    };

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTabelaCliente(db);
        createTabelaPessoa(db);
        createTabelaUsuario(db);
        createTabelaEndereco(db);
        createTabelaContato(db);
        createTabelaRestaurante(db);
        createTabelaProprietario(db);
        createTabelaEspecialidades(db);
        createTabelaPreferencias(db);
        createTabelaAvaliacaoRestaurante(db);
        createTabelaAvaliacaoPrato(db);
        createTabelaPrato(db);
    }

    public void createTabelaCliente(SQLiteDatabase db){
        String sqlTbCliente =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT " +
                        ");";
        sqlTbCliente = String.format(sqlTbCliente,
                TABELA_CLIENTE, CAMPO_ID_CLIENTE, CAMPO_FK_USUARIO_CLIENTE, CAMPO_FK_PREFERENCIAS);

        db.execSQL(sqlTbCliente);

    }

    public void createTabelaPessoa(SQLiteDatabase db){
        String sqlTbPessoa =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT NOT NULL " +
                        ");";
        sqlTbPessoa = String.format(sqlTbPessoa,
                TABELA_PESSOA, CAMPO_ID_PESSOA, CAMPO_NOME, CAMPO_CPF, CAMPO_DATA_NASCIMENTO, CAMPO_GENERO, CAMPO_FK_ENDERECO, CAMPO_FK_CONTATO);

        db.execSQL(sqlTbPessoa);

    }

    public void createTabelaUsuario(SQLiteDatabase db){
        String sqlTbUsuario =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL UNIQUE, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbUsuario = String.format(sqlTbUsuario,
                TABELA_USUARIO, CAMPO_ID_USUARIO, CAMPO_FK_PESSOA, CAMPO_LOGIN, CAMPO_SENHA);
        db.execSQL(sqlTbUsuario);
    }

    public void createTabelaEndereco(SQLiteDatabase db){
        String sqlTbEndereco =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL " +
                        ");";
        sqlTbEndereco = String.format(sqlTbEndereco,
                TABELA_ENDERECO, CAMPO_ID_ENDERECO, CAMPO_CEP, CAMPO_RUA, CAMPO_BAIRRO, CAMPO_CIDADE, CAMPO_NUMERO);
        db.execSQL(sqlTbEndereco);
    }

    public void createTabelaContato(SQLiteDatabase db){
        String sqlTbContato =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT NOT NULL " +
                        ");";
        sqlTbContato = String.format(sqlTbContato,
                TABELA_CONTATO, CAMPO_ID_CONTATO, CAMPO_TELEFONE, CAMPO_EMAIL, CAMPO_SITE);
        db.execSQL(sqlTbContato);
    }

    public void createTabelaRestaurante(SQLiteDatabase db){
        String sqlTbRestaurante =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s TEXT, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT NOT NULL, " +
                        "  %8$s TEXT, " +
                        "  %9$s TEXT " +
                        ");";
        sqlTbRestaurante = String.format(sqlTbRestaurante,
                TABELA_RESTAURANTE, CAMPO_ID_RESTAURANTE, CAMPO_NOME_RESTAURANTE, CAMPO_CNPJ, CAMPO_NOTA_MEDIA, CAMPO_FK_ENDERECO_RESTAURANTE, CAMPO_FK_CONTATO_RESTAURANTE, CAMPO_FK_ESPECIALIDADES, CAMPO_FK_PROPRIETARIO);

        db.execSQL(sqlTbRestaurante);
    }

    public void createTabelaProprietario(SQLiteDatabase db){
        String sqlTbProprietario =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL " +
                        ");";
        sqlTbProprietario = String.format(sqlTbProprietario,
                TABELA_PROPRIETARIO, CAMPO_ID_PROPRIETARIO, CAMPO_FK_USUARIO_PROPRIETARIO);
        db.execSQL(sqlTbProprietario);
    }

    public void createTabelaEspecialidades(SQLiteDatabase db){
        String sqlTbEspecialidades =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s INTEGER NOT NULL, " +
                        "  %4$s INTEGER NOT NULL, " +
                        "  %5$s INTEGER NOT NULL, " +
                        "  %6$s INTEGER NOT NULL, " +
                        "  %7$s INTEGER NOT NULL, " +
                        "  %8$s INTEGER NOT NULL, " +
                        "  %9$s INTEGER NOT NULL, " +
                        "  %10$s INTEGER NOT NULL, " +
                        "  %11$s INTEGER NOT NULL, " +
                        "  %12$s INTEGER NOT NULL, " +
                        "  %13$s INTEGER NOT NULL, " +
                        "  %14$s INTEGER NOT NULL, " +
                        "  %15$s INTEGER NOT NULL, " +
                        "  %16$s INTEGER NOT NULL, " +
                        "  %17$s INTEGER NOT NULL " +
                        ");";
        sqlTbEspecialidades = String.format(sqlTbEspecialidades,
                TABELA_ESPECIALIDADES, CAMPO_ID_ESPECIALIDADES, CAMPO_ACAI_ESP, CAMPO_CHINESA_ESP, CAMPO_BRASILEIRA_ESP, CAMPO_CARNES_ESP,
                CAMPO_CONTEMPORANEA_ESP, CAMPO_ITALIANA_ESP, CAMPO_JAPONESA_ESP, CAMPO_LANCHES_ESP, CAMPO_MARMITA_ESP,
                CAMPO_PIZZA_ESP, CAMPO_SAUDAVEL_ESP, CAMPO_ALACARTE_ESP, CAMPO_RODIZIO_ESP, CAMPO_DELIVERY_ESP, CAMPO_SELFSERVICE_ESP);
        db.execSQL(sqlTbEspecialidades);
    }

    public void createTabelaPreferencias(SQLiteDatabase db){
        String sqlTbPreferencias =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s INTEGER NOT NULL, " +
                        "  %4$s INTEGER NOT NULL, " +
                        "  %5$s INTEGER NOT NULL, " +
                        "  %6$s INTEGER NOT NULL, " +
                        "  %7$s INTEGER NOT NULL, " +
                        "  %8$s INTEGER NOT NULL, " +
                        "  %9$s INTEGER NOT NULL, " +
                        "  %10$s INTEGER NOT NULL, " +
                        "  %11$s INTEGER NOT NULL, " +
                        "  %12$s INTEGER NOT NULL, " +
                        "  %13$s INTEGER NOT NULL, " +
                        "  %14$s INTEGER NOT NULL, " +
                        "  %15$s INTEGER NOT NULL, " +
                        "  %16$s INTEGER NOT NULL, " +
                        "  %17$s INTEGER NOT NULL " +
                        ");";
        sqlTbPreferencias = String.format(sqlTbPreferencias,
                TABELA_PREFERENCIAS, CAMPO_ID_PREFERENCIAS, CAMPO_ACAI_PREF, CAMPO_CHINESA_PREF, CAMPO_BRASILEIRA_PREF, CAMPO_CARNES_PREF,
                CAMPO_CONTEMPORANEA_PREF, CAMPO_ITALIANA_PREF, CAMPO_JAPONESA_PREF, CAMPO_LANCHES_PREF, CAMPO_MARMITA_PREF,
                CAMPO_PIZZA_PREF, CAMPO_SAUDAVEL_PREF, CAMPO_ALACARTE_PREF, CAMPO_RODIZIO_PREF, CAMPO_DELIVERY_PREF, CAMPO_SELFSERVICE_PREF);
        db.execSQL(sqlTbPreferencias);
    }

    public void createTabelaAvaliacaoRestaurante(SQLiteDatabase db){
        String sqlTbAvaliacaoRestaurante =
                "CREATE TABLE %1s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT, " +
                        "  %4$s TEXT, " +
                        "  %5$s TEXT " +
                        ");";
        sqlTbAvaliacaoRestaurante = String.format(sqlTbAvaliacaoRestaurante,
                TABELA_AVALIACAO_RESTAURANTE, CAMPO_ID_AVALIACAO_RESTAURANTE, CAMPO_FK_RESTAURANTE, CAMPO_FK_CLIENTE_AVALIA_RESTAURANTE, CAMPO_NOTA_RESTAURANTE);
        db.execSQL(sqlTbAvaliacaoRestaurante);
    }

    public void createTabelaAvaliacaoPrato(SQLiteDatabase db){
        String sqlTbAvaliacao =
                "CREATE TABLE %1s ( " +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT, " +
                        "  %4$s TEXT, " +
                        "  %5$s TEXT " +
                        ");";
        sqlTbAvaliacao = String.format(sqlTbAvaliacao,
                TABELA_AVALIACAO_PRATO, CAMPO_ID_AVALIACAO_PRATO, CAMPO_FK_PRATO, CAMPO_FK_CLIENTE_AVALIA_PRATO, CAMPO_NOTA_PRATO);
        db.execSQL(sqlTbAvaliacao);
    }

    public void createTabelaPrato(SQLiteDatabase db){
        String sqlTbPrato =
                "CREATE TABLE %1$s ( "  +
                        "  %2$s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  %3$s TEXT NOT NULL, " +
                        "  %4$s TEXT NOT NULL, " +
                        "  %5$s REAL NOT NULL, " +
                        "  %6$s TEXT NOT NULL, " +
                        "  %7$s TEXT " +
                        ");";
        sqlTbPrato = String.format(sqlTbPrato,
                TABELA_PRATO, CAMPO_ID_PRATO, CAMPO_NOME_PRATO, CAMPO_DESCRICAO, CAMPO_PRECO, CAMPO_FK_PRATO_RESTAURANTE, CAMPO_PRATO_NOTA_MEDIA);
        db.execSQL(sqlTbPrato);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        dropTables(db);
        onCreate(db);
    }

    private void dropTables(SQLiteDatabase db){
        StringBuilder dropTables = new StringBuilder();
        for (String tabela: TABELAS){
            dropTables.append(" DROP TABLE IF EXISTS ");
            dropTables.append(tabela);
            dropTables.append("; ");
        }
        db.execSQL(dropTables.toString());
    }

}
