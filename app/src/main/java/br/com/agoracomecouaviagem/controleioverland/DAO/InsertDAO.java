package br.com.agoracomecouaviagem.controleioverland.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import br.com.agoracomecouaviagem.controleioverland.Entities.Despesa;
import br.com.agoracomecouaviagem.controleioverland.Entities.Diario;
import br.com.agoracomecouaviagem.controleioverland.Entities.Estacionamento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Hospedagem;
import br.com.agoracomecouaviagem.controleioverland.Entities.Manutencao;
import br.com.agoracomecouaviagem.controleioverland.Entities.Passeio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Pedagio;
import br.com.agoracomecouaviagem.controleioverland.Entities.Viagem;
import br.com.agoracomecouaviagem.controleioverland.Utils.SQLConnection;
import br.com.agoracomecouaviagem.controleioverland.Entities.Abastecimento;
import br.com.agoracomecouaviagem.controleioverland.Entities.Alimentacao;

public class InsertDAO {

    private SQLConnection conn;
    private SQLiteDatabase db;

    public InsertDAO(Context context){
        conn = new SQLConnection(context);
        db = conn.getWritableDatabase();
    }

    public long addAbastecimento (Abastecimento abastecimento){

        ContentValues values = new ContentValues();

        values.put("data",abastecimento.getData().toString());
        values.put("descricao",abastecimento.getDescricao());
        values.put("valor",abastecimento.getValor());
        values.put("categoria",abastecimento.getCategoria());
        values.put("metodoPagamento",abastecimento.getMetodoPagamento());
        values.put("local", abastecimento.getLocal());
        values.put("quantidadeCombustivel",abastecimento.getQuantidadeCombustivel());
        values.put("valorPorLitro",abastecimento.getValorPorLitro());
        values.put("id_viagem", abastecimento.getId_viagem());
        return db.insert("abastecimento",null,values);
    }

    public long addAlimentacao (Alimentacao alimentacao){

        ContentValues values = new ContentValues();

        values.put("data",alimentacao.getData().toString());
        values.put("descricao",alimentacao.getDescricao());
        values.put("valor",alimentacao.getValor());
        values.put("categoria",alimentacao.getCategoria());
        values.put("metodoPagamento",alimentacao.getMetodoPagamento());
        values.put("local", alimentacao.getLocal());
        values.put("tipoAlimentacao", alimentacao.getTipoAlimentacao());
        values.put("id_viagem", alimentacao.getId_viagem());
        return db.insert("alimentacao",null,values);
    }

    public long addDespesa (Despesa despesa){

        ContentValues values = new ContentValues();

        values.put("data", despesa.getData().toString());
        values.put("descricao",despesa.getDescricao());
        values.put("valor",despesa.getValor());
        values.put("categoria",despesa.getCategoria());
        values.put("metodoPagamento",despesa.getMetodoPagamento());
        values.put("local", despesa.getLocal());
        values.put("id_viagem", despesa.getId_viagem());

        return  db.insert("despesa",null,values);

    }

    public long addDiario(Diario diario){

        ContentValues values = new ContentValues();

        values.put("data", diario.getData().toString());
        values.put("local", diario.getLocal());
        values.put("comentario", diario.getComentario());
        values.put("id_viagem", diario.getId_viagem());
        values.put("favorito", diario.getFavorito());

        return db.insert("diario",null,values);
    }

    public long addEstacionamento (Estacionamento estacionamento){

        ContentValues values = new ContentValues();

        values.put("data", estacionamento.getData().toString());
        values.put("descricao",estacionamento.getDescricao());
        values.put("valor",estacionamento.getValor());
        values.put("categoria",estacionamento.getCategoria());
        values.put("metodoPagamento",estacionamento.getMetodoPagamento());
        values.put("local", estacionamento.getLocal());
        values.put("quantidadeHoras", estacionamento.getQuantidadeHoras());
        values.put("id_viagem", estacionamento.getId_viagem());

        return  db.insert("estacionamento",null,values);

    }

    public long addHospedagem (Hospedagem hospedagem){

        ContentValues values = new ContentValues();

        values.put("data", hospedagem.getData().toString());
        values.put("descricao",hospedagem.getDescricao());
        values.put("valor",hospedagem.getValor());
        values.put("categoria",hospedagem.getCategoria());
        values.put("metodoPagamento",hospedagem.getMetodoPagamento());
        values.put("local", hospedagem.getLocal());
        values.put("tipoHospedagem", hospedagem.getTipoHospedagem());
        values.put("quantidadeDeDiarias", hospedagem.getQuantidadeDeDiarias());
        values.put("nomeHospedagem", hospedagem.getNomeHospedagem());
        values.put("id_viagem", hospedagem.getId_viagem());
        return  db.insert("hospedagem",null,values);

    }

    public long addManutencao (Manutencao manutencao){

        ContentValues values = new ContentValues();

        values.put("data", manutencao.getData().toString());
        values.put("descricao",manutencao.getDescricao());
        values.put("valor",manutencao.getValor());
        values.put("categoria",manutencao.getCategoria());
        values.put("metodoPagamento",manutencao.getMetodoPagamento());
        values.put("local", manutencao.getLocal());
        values.put("tipoDeManutencao", manutencao.getTipoDeManutencao());
        values.put("id_viagem", manutencao.getId_viagem());

        return  db.insert("manutencao",null,values);

    }

    public long addPasseio (Passeio passeio){

        ContentValues values = new ContentValues();

        values.put("data", passeio.getData().toString());
        values.put("descricao",passeio.getDescricao());
        values.put("valor",passeio.getValor());
        values.put("categoria",passeio.getCategoria());
        values.put("metodoPagamento",passeio.getMetodoPagamento());
        values.put("local", passeio.getLocal());
        values.put("nomePasseio", passeio.getNomePasseio());
        values.put("id_viagem", passeio.getId_viagem());

        return  db.insert("passeio",null,values);

    }

    public long addPedagio (Pedagio pedagio){

        ContentValues values = new ContentValues();

        values.put("data", pedagio.getData().toString());
        values.put("descricao",pedagio.getDescricao());
        values.put("valor",pedagio.getValor());
        values.put("categoria",pedagio.getCategoria());
        values.put("metodoPagamento",pedagio.getMetodoPagamento());
        values.put("local", pedagio.getLocal());
        values.put("qualidadeDaVia", pedagio.getQualidadeDaVia());
        values.put("id_viagem", pedagio.getId_viagem());


        return  db.insert("pedagio",null,values);

    }

    public long addViagem(Viagem viagem){

        ContentValues values = new ContentValues();

        values.put("nome", viagem.getNome());
        values.put("dataDeInicio", viagem.getDataDeInicio().toString());
        values.put("dataDeTermino", "Não definido");
        values.put("localDePartida", viagem.getLocalDePartida());
        values.put("destinoFinal", viagem.getDestinoFinal());
        values.put("kilometragemInicial", viagem.getKilometragemInicial());
        values.put("kilometragemParcial", viagem.getKilometragemParcial());
        values.put("kilometragemFinal", viagem.getKilometragemFinal());
        values.put("gastoParcial", viagem.getGastoParcial());
        values.put("gastoTotal", viagem.getGastoTotal());
        values.put("status", viagem.getStatus());

        return db.insert("viagem",null,values);

    }

}
