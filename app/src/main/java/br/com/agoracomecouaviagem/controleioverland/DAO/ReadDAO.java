package br.com.agoracomecouaviagem.controleioverland.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Despesa;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Entities.Estacionamento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.SQLConnection;

public class ReadDAO {

    private SQLConnection conn;
    private SQLiteDatabase db;

    DateTimeFormatter dtf = null;

    public ReadDAO(Context context){
        conn = new SQLConnection(context);
        db = conn.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Abastecimento> readAbastecimento(){
        List<Abastecimento> listAbastecimentos = new ArrayList<>();
        Cursor cursor = db.query(
                "abastecimento",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao", "quantidadeCombustivel", "valorPorLitro","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Abastecimento abastecimento = new Abastecimento();
                abastecimento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                abastecimento.setData(dataDB);
                abastecimento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                abastecimento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                abastecimento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                abastecimento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                abastecimento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                abastecimento.setQuantidadeCombustivel(cursor.getDouble(cursor.getColumnIndex("quantidadeCombustivel")));
                abastecimento.setValorPorLitro(cursor.getDouble(cursor.getColumnIndex("valorPorLitro")));
                abastecimento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                listAbastecimentos.add(abastecimento);
            }
        }
        return listAbastecimentos;
    }

    @SuppressLint("Range")
    public List<Alimentacao> readAlimentacao(){
        List<Alimentacao> listAlimentacao = new ArrayList<>();
        Cursor cursor = db.query(
                "alimentacao",
                new String[] {"id", "data", "valor", "local", "metodoPagamento","categoria", "descricao","tipoAlimentacao","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Alimentacao alimentacao = new Alimentacao();
                alimentacao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                alimentacao.setData(dataDB);
                alimentacao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                alimentacao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                alimentacao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                alimentacao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                alimentacao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                alimentacao.setTipoAlimentacao(cursor.getString(cursor.getColumnIndex("tipoAlimentacao")));
                alimentacao.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                listAlimentacao.add(alimentacao);
            }
        }
        return listAlimentacao;
    }

    @SuppressLint("Range")
    public List<Despesa> readDespesa(){
        List<Despesa> listDespesa = new ArrayList<>();
        Cursor cursor = db.query(
                "despesa",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Despesa despesa = new Despesa();
                despesa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                despesa.setData(dataDB);
                despesa.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                despesa.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                despesa.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                despesa.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                despesa.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                listDespesa.add(despesa);
            }
        }
        return listDespesa;
    }

    @SuppressLint("Range")
    public List<Estacionamento> readEstacionamento(){
        List<Estacionamento> listEstacionamento = new ArrayList<>();
        Cursor cursor = db.query(
                "estacionamento",
                new String[] {"id", "data", "valor", "local", "metodoPagamento","categoria", "descricao", "quantidadeHoras","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range")
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Estacionamento estacionamento = new Estacionamento();
                estacionamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                estacionamento.setData(dataDB);
                estacionamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                estacionamento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                estacionamento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                estacionamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                estacionamento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                estacionamento.setQuantidadeHoras(cursor.getInt(cursor.getColumnIndex("quantidadeHoras")));
                estacionamento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));
                listEstacionamento.add(estacionamento);
            }
        }
        return listEstacionamento;
    }

    @SuppressLint("Range")
    public List<Diario> readDiario(){
        List<Diario> listDiario = new ArrayList<>();
        Cursor cursor = db.query(
                "diario",
                new String[]{"id","data","local","comentario", "favorito"},
                null,null,null,null,"data DESC"
        );

        while (cursor.moveToNext()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);

                Diario diario = new Diario();
                diario.setId(cursor.getInt(cursor.getColumnIndex("id")));
                diario.setData(dataDB);
                diario.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                diario.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
                diario.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                // Recupera o valor da coluna "favorito" e converte para booleano
                int favoritoInt = cursor.getInt(cursor.getColumnIndex("favorito"));
                boolean favorito = favoritoInt != 0; // Converte o valor int para booleano
                diario.setFavorito(favorito);

                listDiario.add(diario);
            }


        }
        return listDiario;

    }

    @SuppressLint("Range")
    public List<Hospedagem> readHospedagem(){
        List<Hospedagem> listHospedagem = new ArrayList<>();
        Cursor cursor = db.query(
                "hospedagem",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao","tipoHospedagem","quantidadeDeDiarias","nomeHospedagem","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Hospedagem hospedagem = new Hospedagem();
                hospedagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                hospedagem.setData(dataDB);
                hospedagem.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                hospedagem.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                hospedagem.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                hospedagem.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                hospedagem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                hospedagem.setTipoHospedagem(cursor.getString(cursor.getColumnIndex("tipoHospedagem")));
                hospedagem.setQuantidadeDeDiarias(cursor.getInt(cursor.getColumnIndex("quantidadeDeDiarias")));
                hospedagem.setNomeHospedagem(cursor.getString(cursor.getColumnIndex("nomeHospedagem")));
                hospedagem.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                listHospedagem.add(hospedagem);
            }
        }
        return listHospedagem;
    }

    @SuppressLint("Range")
    public List<Manutencao> readManutencao(){
        List<Manutencao> listManutencao = new ArrayList<>();
        Cursor cursor = db.query(
                "manutencao",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao","tipoDeManutencao","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Manutencao manutencao = new Manutencao();
                manutencao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                manutencao.setData(dataDB);
                manutencao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                manutencao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                manutencao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                manutencao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                manutencao.setTipoDeManutencao(cursor.getString(cursor.getColumnIndex("tipoDeManutencao")));
                manutencao.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));
                listManutencao.add(manutencao);
            }
        }
        return listManutencao;
    }

    @SuppressLint("Range")
    public List<Passeio> readPasseio(){
        List<Passeio> listPasseio = new ArrayList<>();
        Cursor cursor = db.query(
                "passeio",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao","nomePasseio","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range") LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Passeio passeio = new Passeio();
                passeio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                passeio.setData(dataDB);
                passeio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                passeio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                passeio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                passeio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                passeio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                passeio.setNomePasseio(cursor.getString(cursor.getColumnIndex("nomePasseio")));
                passeio.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));
                listPasseio.add(passeio);
            }
        }
        return listPasseio;
    }

    @SuppressLint("Range")
    public List<Pedagio> readPedagio(){
        List<Pedagio> listPedagio = new ArrayList<>();
        Cursor cursor = db.query(
                "viagem",
                new String[] {"id", "data", "valor", "local", "metodoPagamento", "categoria","descricao","qualidadeDaVia","id_viagem"},
                null,
                null,
                null,
                null,
                "data DESC"
        );

        while(cursor.moveToNext()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                @SuppressLint("Range")
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                Pedagio pedagio = new Pedagio();
                pedagio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                pedagio.setData(dataDB);
                pedagio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                pedagio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                pedagio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                pedagio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                pedagio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                pedagio.setQualidadeDaVia(cursor.getString(cursor.getColumnIndex("qualidadeDaVia")));
                pedagio.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));
                listPedagio.add(pedagio);
            }
        }
        return listPedagio;
    }


    @SuppressLint("Range")
    public List<Viagem> readViagem (){

        List<Viagem> lstViagem = new ArrayList<>();


        Cursor cursor = db.query("viagem", new String []{"id","nome","dataDeInicio","dataDeTermino","localDePartida","destinoFinal","kilometragemInicial",
                "kilometragemParcial"
        ,"kilometragemFinal", "gastoParcial","gastoTotal","status" },null,null,null,null,"dataDeInicio DESC");

        while (cursor.moveToNext()){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("Range")

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                Viagem viagem = new Viagem();
                LocalDate dataInicioDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataDeInicio")), dtf); // esta é a linha 356

                String dataTerminoString = cursor.getString(cursor.getColumnIndex("dataDeTermino"));
                LocalDate dataTerminoDB = null;
                if (!dataTerminoString.equals("Não definido")) {
                    dataTerminoDB = LocalDate.parse(dataTerminoString, dtf);
                }


                viagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                viagem.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                viagem.setDataDeInicio(dataInicioDB);
                viagem.setDataDeTermino(dataTerminoDB);
                viagem.setLocalDePartida(cursor.getString(cursor.getColumnIndex("localDePartida")));
                viagem.setDestinoFinal(cursor.getString(cursor.getColumnIndex("destinoFinal")));
                viagem.setKilometragemInicial(cursor.getDouble(cursor.getColumnIndex("kilometragemInicial")));
                viagem.setKilometragemParcial(cursor.getDouble(cursor.getColumnIndex("kilometragemParcial")));
                viagem.setKilometragemFinal(cursor.getDouble(cursor.getColumnIndex("kilometragemFinal")));
                viagem.setGastoParcial(cursor.getDouble(cursor.getColumnIndex("gastoParcial")));
                viagem.setGastoTotal(cursor.getDouble(cursor.getColumnIndex("gastoTotal")));
                int statusInt = cursor.getInt(cursor.getColumnIndex("status"));
                boolean status = (statusInt == 1); // Se o valor for 1, é true; caso contrário, é false
                viagem.setStatus(status);

                lstViagem.add(viagem);

            }


        }
        return  lstViagem;
    }


    @SuppressLint("Range")
    public Viagem readViagemById(int id) {
        Viagem viagemById = new Viagem();

        Cursor cursor = null;
        try {
            cursor = db.query(
                    "viagem",
                    new String[]{"id", "nome", "dataDeInicio", "dataDeTermino", "localDePartida", "destinoFinal", "kilometragemInicial",
                            "kilometragemParcial", "kilometragemFinal", "gastoParcial", "gastoTotal", "status"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    "dataDeInicio DESC"
            );

            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataInicioDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataInicioDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("dataDeInicio")), dtf);
                }

                String dataTerminoString = cursor.getString(cursor.getColumnIndex("dataDeTermino"));
                LocalDate dataTerminoDB = null;
                if (!dataTerminoString.equals("Não definido")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dataTerminoDB = LocalDate.parse(dataTerminoString, dtf);
                    }
                }

                viagemById.setId(cursor.getInt(cursor.getColumnIndex("id")));
                viagemById.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                viagemById.setDataDeInicio(dataInicioDB);
                viagemById.setDataDeTermino(dataTerminoDB);
                viagemById.setLocalDePartida(cursor.getString(cursor.getColumnIndex("localDePartida")));
                viagemById.setDestinoFinal(cursor.getString(cursor.getColumnIndex("destinoFinal")));
                viagemById.setKilometragemInicial(cursor.getDouble(cursor.getColumnIndex("kilometragemInicial")));
                viagemById.setKilometragemParcial(cursor.getDouble(cursor.getColumnIndex("kilometragemParcial")));
                viagemById.setKilometragemFinal(cursor.getDouble(cursor.getColumnIndex("kilometragemFinal")));
                viagemById.setGastoParcial(cursor.getDouble(cursor.getColumnIndex("gastoParcial")));
                viagemById.setGastoTotal(cursor.getDouble(cursor.getColumnIndex("gastoTotal")));
                int statusInt = cursor.getInt(cursor.getColumnIndex("status"));
                boolean status = (statusInt == 1);
                viagemById.setStatus(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {

            }
        }

        return viagemById;
    }

    @SuppressLint("Range")
    public Abastecimento readAbastecimentoById(int id) {

        Abastecimento abastecimento = new Abastecimento();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "abastecimento",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local",
                            "quantidadeCombustivel", "valorPorLitro", "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                abastecimento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                abastecimento.setData(dataDB);
                abastecimento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                abastecimento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                abastecimento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                abastecimento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                abastecimento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                abastecimento.setQuantidadeCombustivel(cursor.getDouble(cursor.getColumnIndex("quantidadeCombustivel")));
                abastecimento.setValorPorLitro(cursor.getDouble(cursor.getColumnIndex("valorPorLitro")));
                abastecimento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Abastecimento" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return abastecimento;
    }

    @SuppressLint("Range")
    public Alimentacao readAlimentacaoById(int id) {

        Alimentacao alimentacao = new Alimentacao();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "alimentacao",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local",
                            "tipoAlimentacao", "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                alimentacao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                alimentacao.setData(dataDB);
                alimentacao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                alimentacao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                alimentacao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                alimentacao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                alimentacao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                alimentacao.setTipoAlimentacao(cursor.getString(cursor.getColumnIndex("tipoAlimentacao")));
                alimentacao.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Alimentacao" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return alimentacao;
    }

    @SuppressLint("Range")
    public Despesa readDespesaById(int id) {

        Despesa despesa = new Despesa();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "despesa",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                despesa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                despesa.setData(dataDB);
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                despesa.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                despesa.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                despesa.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                despesa.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                despesa.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Alimentacao" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return despesa;
    }

    @SuppressLint("Range")
    public Diario readDiarioById(int id) {

        Diario diario = new Diario();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "diario",
                    new String[]{"id", "data", "local", "comentario",
                            "id_viagem", "favorito"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                diario.setId(cursor.getInt(cursor.getColumnIndex("id")));
                diario.setData(dataDB);
                diario.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                diario.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
                diario.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                // Recupera o valor da coluna "favorito" e converte para booleano
                int favoritoInt = cursor.getInt(cursor.getColumnIndex("favorito"));
                boolean favorito = favoritoInt != 0; // Converte o valor int para booleano
                diario.setFavorito(favorito);
            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Diario" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return diario;
    }

    @SuppressLint("Range")
    public Estacionamento readEstacionamentoById(int id) {

        Estacionamento estacionamento = new Estacionamento();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "estacionamento",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local", "quantidadeHoras",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                estacionamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                estacionamento.setData(dataDB);
                estacionamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                estacionamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                estacionamento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                estacionamento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                estacionamento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                estacionamento.setQuantidadeHoras(cursor.getInt(cursor.getColumnIndex("quantidadeHoras")));
                estacionamento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Estacionamento" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return estacionamento;
    }

    @SuppressLint("Range")
    public Hospedagem readHospedagemById(int id) {

        Hospedagem hospedagem = new Hospedagem();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "hospedagem",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local", "tipoHospedagem", "quantidadeDeDiarias", "nomeHospedagem",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                hospedagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                hospedagem.setData(dataDB);
                hospedagem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                hospedagem.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                hospedagem.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                hospedagem.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                hospedagem.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                hospedagem.setTipoHospedagem(cursor.getString(cursor.getColumnIndex("tipoHospedagem")));
                hospedagem.setQuantidadeDeDiarias(cursor.getInt(cursor.getColumnIndex("quantidadeDeDiarias")));
                hospedagem.setNomeHospedagem(cursor.getString(cursor.getColumnIndex("nomeHospedagem")));
                hospedagem.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Alimentacao" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return hospedagem;
    }

    @SuppressLint("Range")
    public Manutencao readManutencaoById(int id) {

        Manutencao manutencao = new Manutencao();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "manutencao",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local", "tipoDeManutencao",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                manutencao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                manutencao.setData(dataDB);
                manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                manutencao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                manutencao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                manutencao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                manutencao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                manutencao.setTipoDeManutencao(cursor.getString(cursor.getColumnIndex("tipoDeManutencao")));
                manutencao.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Manutencao" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return manutencao;
    }

    @SuppressLint("Range")
    public Passeio readPasseioById(int id) {

        Passeio passeio = new Passeio();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "passeio",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local", "nomePasseio",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                passeio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                passeio.setData(dataDB);
                passeio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                passeio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                passeio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                passeio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                passeio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                passeio.setNomePasseio(cursor.getString(cursor.getColumnIndex("nomePasseio")));
                passeio.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Passeio" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return passeio;
    }

    @SuppressLint("Range")
    public Pedagio readPedagioById(int id) {

        Pedagio pedagio = new Pedagio();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    "pedagio",
                    new String[]{"id", "data", "descricao", "valor", "categoria", "metodoPagamento", "local", "qualidadeDaVia",
                            "id_viagem"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );
            if (cursor.moveToFirst()) {
                DateTimeFormatter dtf = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                }

                LocalDate dataDB = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                }

                pedagio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                pedagio.setData(dataDB);
                pedagio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                pedagio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                pedagio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                pedagio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                pedagio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                pedagio.setQualidadeDaVia(cursor.getString(cursor.getColumnIndex("qualidadeDaVia")));
                pedagio.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

            }

        } catch (Exception e) {
            Toast.makeText(null, "Erro ao fazer o Read Pedagio" + e.getMessage(), Toast.LENGTH_LONG).show();

        }

        return pedagio;
    }




    @SuppressLint("Range")
    public List<Abastecimento> readAbastecimentoByIdViagem(int id_viagem) {

        List<Abastecimento> lstAbastecimentoFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "abastecimento",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao", "quantidadeCombustivel", "valorPorLitro", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Abastecimento abastecimento = new Abastecimento();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                abastecimento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                abastecimento.setData(dataDB);
                abastecimento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                abastecimento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                abastecimento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                abastecimento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                abastecimento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                abastecimento.setQuantidadeCombustivel(cursor.getDouble(cursor.getColumnIndex("quantidadeCombustivel")));
                abastecimento.setValorPorLitro(cursor.getDouble(cursor.getColumnIndex("valorPorLitro")));
                abastecimento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));


                lstAbastecimentoFiltrado.add(abastecimento);
            }

        }
        return lstAbastecimentoFiltrado;
    }

    @SuppressLint("Range")
    public List<Alimentacao> readAlimentacaoByIdViagem(int id_viagem) {

        List<Alimentacao> lstAlimentacaoFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "alimentacao",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao", "tipoAlimentacao", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Alimentacao alimentacao = new Alimentacao();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                alimentacao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                alimentacao.setData(dataDB);
                alimentacao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                alimentacao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                alimentacao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                alimentacao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                alimentacao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                alimentacao.setTipoAlimentacao(cursor.getString(cursor.getColumnIndex("tipoAlimentacao")));


                lstAlimentacaoFiltrado.add(alimentacao);

            }

        }
        return lstAlimentacaoFiltrado;
    }

    @SuppressLint("Range")
    public List<Despesa> readDespesaByIdViagem(int id_viagem) {

        List<Despesa> lstDespesaFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "despesa",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Despesa despesa = new Despesa();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                despesa.setId(cursor.getInt(cursor.getColumnIndex("id")));
                despesa.setData(dataDB);
                despesa.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                despesa.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                despesa.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                despesa.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                despesa.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));



                lstDespesaFiltrado.add(despesa);

            }

        }
        return lstDespesaFiltrado;
    }

    @SuppressLint("Range")
    public List<Diario> readDiarioByIdViagem(int id_viagem) {

        List<Diario> lstDiarioFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "diario",
                new String[]{"id", "data", "local", "comentario", "id_viagem", "favorito"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Diario diario = new Diario();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                diario.setId(cursor.getInt(cursor.getColumnIndex("id")));
                diario.setData(dataDB);
                diario.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                diario.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
                diario.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                // Recupera o valor da coluna "favorito" e converte para booleano
                int favoritoInt = cursor.getInt(cursor.getColumnIndex("favorito"));
                boolean favorito = favoritoInt != 0; // Converte o valor int para booleano
                diario.setFavorito(favorito);


                lstDiarioFiltrado.add(diario);

            }

        }
        return lstDiarioFiltrado;
    }
    @SuppressLint("Range")
    public List<Diario> readDiarioFavoritoByIdViagem(int id_viagem) {

        List<Diario> lstDiarioFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "diario",
                new String[]{"id", "data", "local", "comentario", "id_viagem", "favorito"},
                "id_viagem = ? and favorito = true",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Diario diario = new Diario();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                diario.setId(cursor.getInt(cursor.getColumnIndex("id")));
                diario.setData(dataDB);
                diario.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                diario.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
                diario.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));

                // Recupera o valor da coluna "favorito" e converte para booleano
                int favoritoInt = cursor.getInt(cursor.getColumnIndex("favorito"));
                boolean favorito = favoritoInt != 0; // Converte o valor int para booleano
                diario.setFavorito(favorito);


                lstDiarioFiltrado.add(diario);

            }

        }
        return lstDiarioFiltrado;
    }


    @SuppressLint("Range")
    public List<Estacionamento> readEstacionamentoByIdViagem(int id_viagem) {

        List<Estacionamento> lstEstacionamentoFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "estacionamento",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao", "quantidadeHoras", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Estacionamento estacionamento = new Estacionamento();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                estacionamento.setId(cursor.getInt(cursor.getColumnIndex("id")));
                estacionamento.setData(dataDB);
                estacionamento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                estacionamento.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                estacionamento.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                estacionamento.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                estacionamento.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                estacionamento.setQuantidadeHoras(cursor.getInt(cursor.getColumnIndex("quantidadeHoras")));
                estacionamento.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));


                lstEstacionamentoFiltrado.add(estacionamento);
            }



        }
        return lstEstacionamentoFiltrado;
    }

    @SuppressLint("Range")
    public List<Hospedagem> readHospedagemByIdViagem(int id_viagem) {

        List<Hospedagem> lstHospedagemFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "hospedagem",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao", "tipoHospedagem","quantidadeDeDiarias", "nomeHospedagem", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Hospedagem hospedagem = new Hospedagem();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                hospedagem.setId(cursor.getInt(cursor.getColumnIndex("id")));
                hospedagem.setData(dataDB);
                hospedagem.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                hospedagem.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                hospedagem.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                hospedagem.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                hospedagem.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                hospedagem.setTipoHospedagem(cursor.getString(cursor.getColumnIndex("tipoHospedagem")));
                hospedagem.setQuantidadeDeDiarias(cursor.getInt(cursor.getColumnIndex("quantidadeDeDiarias")));
                hospedagem.setNomeHospedagem(cursor.getString(cursor.getColumnIndex("nomeHospedagem")));
                hospedagem.setId_viagem(cursor.getInt(cursor.getColumnIndex("id_viagem")));


                lstHospedagemFiltrado.add(hospedagem);
            }



        }
        return lstHospedagemFiltrado;
    }

    @SuppressLint("Range")
    public List<Manutencao> readManutencaoByIdViagem(int id_viagem) {

        List<Manutencao> lstManutencaoFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "manutencao",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao","tipoDeManutencao", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Manutencao manutencao = new Manutencao();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                manutencao.setId(cursor.getInt(cursor.getColumnIndex("id")));
                manutencao.setData(dataDB);
                manutencao.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                manutencao.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                manutencao.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                manutencao.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                manutencao.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                manutencao.setTipoDeManutencao(cursor.getString(cursor.getColumnIndex("tipoDeManutencao")));



                lstManutencaoFiltrado.add(manutencao);

            }

        }
        return lstManutencaoFiltrado;
    }

    @SuppressLint("Range")
    public List<Passeio> readPasseioByIdViagem(int id_viagem) {

        List<Passeio> lstPasseioFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "passeio",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao","nomePasseio","id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Passeio passeio = new Passeio();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                passeio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                passeio.setData(dataDB);
                passeio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                passeio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                passeio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                passeio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                passeio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                passeio.setNomePasseio(cursor.getString(cursor.getColumnIndex("nomePasseio")));


                lstPasseioFiltrado.add(passeio);

            }

        }
        return lstPasseioFiltrado;
    }

    @SuppressLint("Range")
    public List<Pedagio> readPedagioByIdViagem(int id_viagem) {

        List<Pedagio> lstPedagioFiltrado = new ArrayList<>();
        Cursor cursor = db.query(
                "pedagio",
                new String[]{"id", "data", "valor", "local", "metodoPagamento", "categoria", "descricao","qualidadeDaVia", "id_viagem"},
                "id_viagem = ?",
                new String[]{String.valueOf(id_viagem)},
                null,
                null,
                "data DESC, id DESC"
        );


        while (cursor.moveToNext()) {

            Pedagio pedagio = new Pedagio();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dataDB = LocalDate.parse(cursor.getString(cursor.getColumnIndex("data")), dtf);
                pedagio.setId(cursor.getInt(cursor.getColumnIndex("id")));
                pedagio.setData(dataDB);
                pedagio.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                pedagio.setLocal(cursor.getString(cursor.getColumnIndex("local")));
                pedagio.setCategoria(cursor.getString(cursor.getColumnIndex("categoria")));
                pedagio.setMetodoPagamento(cursor.getString(cursor.getColumnIndex("metodoPagamento")));
                pedagio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                pedagio.setQualidadeDaVia(cursor.getString(cursor.getColumnIndex("qualidadeDaVia")));


                lstPedagioFiltrado.add(pedagio);

            }

        }
        return lstPedagioFiltrado;
    }










    @SuppressLint("Range")
    public Double valorTotal(String table) {
        Double valorTotal = 0.0;
        Cursor cursor = db.query(
                table,
                new String[]{"valor"},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Abastecimento abastecimento = new Abastecimento();
            abastecimento.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
            valorTotal += abastecimento.getValor();
        }

        return valorTotal;


    }

    public Double calcularGastoTotal(int idViagem) {
        Double gastoTotal = 0.0;

        // Somar os valores de todas as tabelas
        gastoTotal += calcularGastoTotalTabela("abastecimento", idViagem);
        gastoTotal += calcularGastoTotalTabela("alimentacao", idViagem);
        gastoTotal += calcularGastoTotalTabela("despesa", idViagem);
        gastoTotal += calcularGastoTotalTabela("estacionamento", idViagem);
        gastoTotal += calcularGastoTotalTabela("hospedagem", idViagem);
        gastoTotal += calcularGastoTotalTabela("manutencao", idViagem);
        gastoTotal += calcularGastoTotalTabela("passeio", idViagem);
        gastoTotal += calcularGastoTotalTabela("pedagio", idViagem);

        return gastoTotal;
    }

    public Double calcularGastoTotalTabela(String tabela, int idViagem) {
        String query = "SELECT SUM(valor) FROM " + tabela + " WHERE id_viagem = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idViagem)});

        Double gastoTotalTabela = 0.0;
        if (cursor.moveToFirst()) {
            gastoTotalTabela = cursor.getDouble(0);
        }


        return gastoTotalTabela;
    }




    @SuppressLint("Range")
    public double CalcularGastosPorMesByIdViagem(int idViagem, int mes, int ano) {
        double totalGastos = 0.0;

        // Formatar a data para comparar com o formato "MM/yyyy"
        String mesAno = String.format("%02d/%04d", mes, ano);

        // Array com os nomes das tabelas
        String[] tabelas = {"abastecimento", "alimentacao", "despesa", "estacionamento", "hospedagem", "manutencao", "passeio", "pedagio"};

        // Iterar sobre as tabelas e calcular o total de gastos
        for (String tabela : tabelas) {
            Cursor cursor = db.rawQuery("SELECT SUM(valor) as total FROM " + tabela +
                    " WHERE id_viagem = ? AND strftime('%m/%Y', data) = ?", new String[]{String.valueOf(idViagem), mesAno});
            if (cursor.moveToFirst()) {
                totalGastos += cursor.getDouble(cursor.getColumnIndex("total"));
            }

        }
        return totalGastos;
    }


}
