package br.com.agoracomecouaviagem.controleioverland.Utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLConnection extends SQLiteOpenHelper {

    private static final String name = "bancoApp.db";
    private static final int version = 1;
    private Context context;
    public SQLConnection (Context context){
        super(context,name,null,version);
        this.context = context;
    }

    @Override
    public void onCreate (SQLiteDatabase sql){

        String criarDiario = "CREATE TABLE IF NOT EXISTS diario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "data varchar(11) NOT NULL," +
                "local varchar(100)," +
                "comentario varchar(2000)," +
                "favorito boolean NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id)" +
                ");";


        String criarDespesa = "CREATE TABLE IF NOT EXISTS despesa (" + //CRIANDO TABELA DESPESA
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100),"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarAbastecimento =
                "CREATE TABLE IF NOT EXISTS abastecimento (" + //CRIANDO TABELA ABASTECIMENTO
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "data VARCHAR(11) NOT NULL, " +
                        "descricao VARCHAR(200), " +
                        "valor REAL NOT NULL, " +
                        "categoria VARCHAR(50) NOT NULL, " +
                        "metodoPagamento VARCHAR(30), " +
                        "local VARCHAR(100), " +
                        "quantidadeCombustivel REAL NOT NULL, " +
                        "valorPorLitro REAL NOT NULL,"+
                        "id_viagem INTEGER," +
                        "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarAlimentacao = "CREATE TABLE IF NOT EXISTS alimentacao (" + //CRIANDO TABELA ALIMENTACAO
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100), " +
                "tipoAlimentacao varchar(30) NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarEstacionamento ="CREATE TABLE IF NOT EXISTS estacionamento (" + //CRIANDO A TABELA ESTACIONAMENTO
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100), " +
                "quantidadeHoras INTEGER, " +
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarHospedagem ="CREATE TABLE IF NOT EXISTS hospedagem (" + //CRIANDO A TABELA HOSPEDAGEM
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100), " +
                "tipoHospedagem varchar(20), " +
                "quantidadeDeDiarias INTEGER NOT NULL,"+
                "nomeHospedagem varchar(120) NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarManutencao = "CREATE TABLE IF NOT EXISTS manutencao (" + //CRIANDO A TABELA MANUTENCAO
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100), " +
                "tipoDeManutencao TEXT NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarPasseio = "CREATE TABLE IF NOT EXISTS passeio (" + //CRIANDO A TABELA PASSEIO
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30)," +
                "local varchar(100), " +
                "nomePasseio TEXT NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarPedagio = "CREATE TABLE IF NOT EXISTS pedagio (" + //CRIANDO A TABELA PEDAGIO
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data varchar(11) NOT NULL, " +
                "descricao varchar(200) NOT NULL, " +
                "valor REAL NOT NULL, " +
                "categoria varchar(50), " +
                "metodoPagamento varchar(30), " +
                "local varchar(100), " +
                "qualidadeDaVia varchar(12) NOT NULL,"+
                "id_viagem INTEGER," +
                "FOREIGN KEY (id_viagem) REFERENCES viagem(id));";

        String criarViagem = "CREATE TABLE IF NOT EXISTS viagem (" + //CRIANDO A TABELA VIAGEM
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome varchar(100) NOT NULL,"+
                "dataDeInicio varchar(11) NOT NULL, " +
                "dataDeTermino varchar(11), " +
                "localDePartida varchar(80) NOT NULL, " +
                "destinoFinal varchar(80), " +
                "kilometragemInicial REAL NOT NULL,"+
                "kilometragemParcial REAL,"+
                "kilometragemFinal REAL,"+
                "gastoParcial REAL, " +
                "gastoTotal REAL, " +
                "status boolean NOT NULL);";




        try{

            sql.execSQL(criarViagem);
            sql.execSQL(criarDiario);
            sql.execSQL(criarDespesa);
            sql.execSQL(criarAbastecimento);
            sql.execSQL(criarAlimentacao);
            sql.execSQL(criarEstacionamento);
            sql.execSQL(criarHospedagem);
            sql.execSQL(criarManutencao);
            sql.execSQL(criarPasseio);
            sql.execSQL(criarPedagio);

            Toast.makeText(context, "Banco de dados criado com sucesso.", Toast.LENGTH_SHORT).show();

        }catch (SQLException e){

            Toast.makeText(context, "Erro ao criar tabelas: " + e.getMessage(), Toast.LENGTH_SHORT).show();

        }




    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
